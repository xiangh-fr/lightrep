package fung.xiangh.lightrep.tools;

import java.math.BigInteger;
import java.util.*;

public class HashTools {

    /**
     * well, we now it's int, and some part of the below code definitely relies on that...
     */
    private static final int HASH_SIZE = 32;

    public static <K> int getOptimalDepth(Collection<K> keys) {
        Map<Integer, Integer> depths = new DefaultingMap<Integer, Integer>(0);
        for (K key : keys) {
            int hash = key.hashCode();
            depths.put(hash, depths.get(hash) + 1);
        }
        return Collections.max(depths.values());
    }

    /**
     * @return integer to be interpreted as a bit array. Interesting bits are marked as 1.
     */
    public static <K> int getRelevantBits(Collection<K> keys) {
        if(keys.size()<2)
        {
            return 1;
        }
        BigInteger[] vectors = buildVectors(keys);
        BigInteger allSet=BigInteger.ONE.shiftLeft(keys.size()).subtract(BigInteger.ONE);
        for (int i = 0; i < vectors.length; i++) {
            if(vectors[i].equals(BigInteger.ZERO) ||vectors[i].equals(allSet))
            {
                vectors[i] = null;
                continue;
            }
            for (int j = 0; j < i; j++) {
                if (vectors[j] != null && isRedundant(vectors[i], vectors[j], allSet)) {
                    vectors[i] = null;
                    break;
                }
            }
        }
        return convert2int(vectors);
    }

    public static <K> int countRelevantBits(Collection<K> keys) {
        return Integer.bitCount(getRelevantBits(keys));
    }

    public static String getReducerString(int relevantBits, String var)
    {
        if(relevantBits == 0xFFFFFFFF || relevantBits == 0)
        {
            return var;
        }
        int move = 0;
        int maskUp = 0;
        StringBuilder res=new StringBuilder();
        for(; relevantBits !=0 ; relevantBits>>>=1)
        {
            switch(relevantBits&1)
            {
                case 0:
                    if(maskUp>0)
                    {
                       appendMask(res, var, maskUp, move);
                        move+=maskUp;
                        maskUp=0;
                    }
                    move++;
                    break;
                case 1:
                    maskUp++;
                    break;
            }
        }
        if(maskUp>0) {
            appendMask(res, var, maskUp, move);
        }
        return res.toString();
    }

    private static void appendMask(StringBuilder res, String var, int maskUp, int move) {
        if(res.length()>0)
        {
            res.append("|");
        }
        int mask=(1<<maskUp)-1;
        res.append("((").append(var);
        if(move>0)
        {
            res.append(">>>").append(move);
        }
        res.append(")&").append(mask).append(")");
    }

    private static int convert2int(Object[] vectors) {
        int res = 0;
        for (int i = 0; i < HASH_SIZE; i++) {
            if (vectors[i] != null) {
                res |= 1 << i;
            }
        }
        return res;
    }

    /**
     * for now only 1/1 comparison, can likely be improved (i.e, a vector can be deduced from 2 others).
     * e.g. if an index is isolated by a vector, it can be disregarded in following vector analysis
     * (could be a first loop)
     */
    private static boolean isRedundant(BigInteger a, BigInteger b, BigInteger mask) {
        BigInteger xor=a.xor(b);
        return  xor.equals(BigInteger.ZERO) || xor.equals(mask);
    }


    private static <K> BigInteger[] buildVectors(Collection<K> keys) {
        BigInteger[] vectors = new BigInteger[HASH_SIZE];
        Arrays.fill(vectors, BigInteger.ZERO);
        int keyIndex = 0;
        for (K key : keys) {
            int hash = key.hashCode();
            for (int i = 0; i < HASH_SIZE; i++) {
                if ((hash & (1 << i)) != 0) {
                    vectors[i] = vectors[i].setBit(keyIndex);
                }
            }
            keyIndex++;
        }
        return vectors;
    }
}
