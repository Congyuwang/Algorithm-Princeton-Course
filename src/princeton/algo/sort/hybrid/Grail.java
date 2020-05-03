package princeton.algo.sort.hybrid;

import princeton.algo.sort.Util;
import java.lang.reflect.Array;
import java.util.Comparator;

/**
 * (c) 2013 by Andrey Astrelin.
 * Refactored by MusicTheorist.
 * Changed to static methods and added javadoc by Congyuwang.
 *
 * For sorting w/ fixed external buffer (512 items),
 * use sortWithBuffer().
 *
 * For sorting w/ dynamic external buffer (sqrt(length)),
 * use sortWithDynBuffer().
 */
final public class Grail {

    private Grail() {}

    private static final int GRAIL_STATIC_BUFFER_LEN = 512;

	private static class GrailState {
        private final int leftOverLen;
        private final int leftOverFrag;

        GrailState(int len, int frag) {
            this.leftOverLen = len;
            this.leftOverFrag = frag;
        }

        int getLeftOverLen() {
            return leftOverLen;
        }

        int getLeftOverFrag() {
            return leftOverFrag;
        }
    }

    /**
     * Swap two intervals in an array--[p1, p1 + swapLength + 1) and [p2, p2 +
     * swapLength + 1). The operation takes ~{@code swapLength} time.
     *
     * @param <T>        type T
     * @param arr        input array
     * @param p1         the beginning point of the first segment
     * @param p2         the beginning point of the second segment
     * @param swapLength the length of the segment
     */
    private static <T> void multiSwap(T[] arr, int p1, int p2, int swapLength) {
        while (swapLength != 0) {
            Util.exch(arr, p1++, p2++);
            swapLength--;
        }
    }

    /**
     * Rotate the subarray of {@code arr} of length ({@code lenA} + {@code lenB})
     * starting from point {@code pos} for {@code lenA} steps to the left.
     *
     * @param <T>  type T
     * @param arr  input array
     * @param pos  the beginning point of rotation
     * @param lenA the length of the segement to be rotated to the right
     * @param lenB the length of the segement to be rotated to the left
     */
    private static <T> void rotate(T[] arr, int pos, int lenA, int lenB) {
        while (lenA != 0 && lenB != 0) {
            if (lenA <= lenB) {
                multiSwap(arr, pos, pos + lenA, lenA);
                pos += lenA;
                lenB -= lenA;
            } else {
                multiSwap(arr, pos + (lenA - lenB), pos + lenA, lenB);
                lenA -= lenB;
            }
        }
    }

    /**
     * Binary insert sort the first {@code len} elements of the array
     *
     * @param arr input array
     * @param len the length starting from {@code arr[0]} to be sorted
     * @param c   the comparator of type T
     * @param <T> the type to be compared
     */
    private static <T> void binaryInsertSort(T[] arr, int len, Comparator<? super T> c) {
        for (int i = 1; i < len; i++) {

            int insertPos = binarySearch(arr, 0, i, arr[i], false, c);

            if (insertPos < i) {
                T item = arr[i];

                // Used TimSort's Binary Insert as a reference here.
                int shifts = i - insertPos;
                switch (shifts) {
                    case 2:
                        arr[insertPos + 2] = arr[insertPos + 1];
                    case 1:
                        arr[insertPos + 1] = arr[insertPos];
                        break;
                    default:
                        System.arraycopy(arr, insertPos, arr, insertPos + 1, shifts);
                }
                arr[insertPos] = item;
            }
        }
    }

    /**
     * This binary search function searches the sub-array of {@code arr} starting
     * from position {@code pos} of length {@code len}. It returns the leftmost
     * position <b>in the sub-array</b> ({@code 0} means the {@code arr[pos]}) where
     * the {@code key} can be inserted (and maintain the order of the subarray), of
     * the rightmost position <b>in the sub-array</b> where the {@code key} can be
     * inserted if {@code isLeft} is set to {@code false}.
     *
     * @param arr    the input array
     * @param pos    the starting point of the sub-array to be searched
     * @param len    the length of the sub-array to be searched
     * @param key    the key to be searched
     * @param isLeft whether return the leftmost or the rightmost position of
     *               {@code key}
     * @param c      comparator of type {@code <T>}
     * @param <T>    type for comparator
     * @return returns the leftmost position <b>in the sub-array</b> ({@code 0}
     *         means the {@code arr[pos]}) where the {@code key} can be inserted
     *         (maintains the order of the subarray), of the rightmost position
     *         <b>in the sub-array</b> if {@code isLeft} is set to {@code false}.
     */
    private static <T> int binarySearch(T[] arr, int pos, int len, T key, boolean isLeft, Comparator<? super T> c) {
        assert pos >= 0;
        assert len >= 0;

        int left = -1;
        int right = len;

        if (isLeft) {
            while (left < right - 1) {
                int mid = left + ((right - left) >> 1);
                if (c.compare(arr[pos + mid], key) >= 0) {
                    right = mid;
                } else {
                    left = mid;
                }
            }
        } else {
            while (left < right - 1) {
                int mid = left + ((right - left) >> 1);
                if (c.compare(arr[pos + mid], key) > 0) {
                    right = mid;
                } else {
                    left = mid;
                }
            }
        }
        return right;
    }

    private static <T> int getKeys(T[] arr, int len, int numKeys, Comparator<? super T> c) {
        int foundKeys = 1;
        int firstKey = 0;
        int M = 1;

        while (M < len && foundKeys < numKeys) {

            int loc = binarySearch(arr, firstKey, foundKeys, arr[M], true, c);

            if (loc == foundKeys || c.compare(arr[M], arr[(firstKey + loc)]) != 0) {
                rotate(arr, firstKey, foundKeys, M - (firstKey + foundKeys));
                firstKey = M - foundKeys;
                rotate(arr, (firstKey + loc), foundKeys - loc, 1);
                foundKeys++;
            }
            M++;
        }
        rotate(arr, 0, firstKey, foundKeys);

        return foundKeys;
    }

    /**
     * Merge {@code arr[pos + len1, pos + len1 + len2 + 1)} and
     * {@code arr[pos, pos + len1 + 1)} by rotation. The worst case cost is
     * {@code min(len1, len2)^2 + max(len1, len2)}. In the worst case, only one item
     * is rotated to the right position at a time.
     *
     * @param arr  the input array
     * @param pos  starting position
     * @param len1 length of the first half
     * @param len2 length of the second half
     * @param c    the Comparator of type {@code <T>}
     * @param <T>  type to be compared
     */
    private static <T> void mergeWithoutBuffer(T[] arr, int pos, int len1, int len2, Comparator<? super T> c) {
        if (len1 < len2) {
            while (len1 != 0) {
                // Binary Search left of arr[pos + len1, pos + len1 + len2 + 1)
                int loc = binarySearch(arr, pos + len1, len2, arr[pos], true, c);

                if (loc != 0) {
                    rotate(arr, pos, len1, loc);

                    pos += loc;
                    len2 -= loc;
                }

                if (len2 == 0) {
                    break;
                }

                do {
                    pos++;
                    len1--;
                } while (len1 != 0 && c.compare(arr[pos], arr[pos + len1]) <= 0);
            }
        } else {
            while (len2 != 0) {
                // Binary Search right of arr[pos, pos + len1 + 1)
                int loc = binarySearch(arr, pos, len1, arr[pos + (len1 + len2 - 1)], false, c);

                if (loc != len1) {
                    rotate(arr, pos + loc, len1 - loc, len2);
                    len1 = loc;
                }

                if (len1 == 0) {
                    break;
                }

                do {
                    len2--;
                } while (len2 != 0 && c.compare(arr[pos + len1 - 1], arr[pos + len1 + len2 - 1]) <= 0);
            }
        }
    }

    /**
     * {@code arr[pos+M, pos)} is the buffer (-pos <= M < 0). Merge
     * {@code arr[pos, pos+leftLen)} and
     * {@code arr[pos+leftLen, pos+leftLen+rightLen)} to
     * {@code arr[pos+M, pos+M+leftLen+rightLen)}. The buffer should be longer than
     * the right part.
     *
     * @param pos the point where buffer ends
     * @param M   buffer size (negative number)
     */
    private static <T> void mergeLeft(T[] arr, int pos, int leftLen, int rightLen, int M, Comparator<? super T> c) {

        int left = 0;
        int right = leftLen;
        rightLen += leftLen;

        while (right < rightLen) {
            if (left == leftLen || c.compare(arr[pos + left], arr[pos + right]) > 0) {
                Util.exch(arr, pos + (M++), pos + (right++));
            } else {
                Util.exch(arr, pos + (M++), pos + (left++));
            }
        }
        // if right hits rightLen before left hits leftLen
        if (M != left) {
            multiSwap(arr, pos + M, pos + left, leftLen - left);
        }
    }

    /**
     * {@code arr[pos+leftLen+rightLen, pos+leftLen+rightLen+M)} is the buffer (M >
     * 0). Merge {@code arr[pos, pos+leftLen)} and
     * {@code arr[pos+leftLen, pos+leftLen+rightLen)} to
     * {@code arr[pos+M, pos+M+leftLen+rightLen)}. The buffer should be longer than
     * the left part.
     *
     * @param pos the point where buffer ends
     * @param M   buffer size (poitive number)
     */
    private static <T> void mergeRight(T[] arr, int pos, int leftLen, int rightLen, int M, Comparator<? super T> c) {
        int mergedPos = leftLen + rightLen + M - 1;
        int right = leftLen + rightLen - 1;
        int left = leftLen - 1;

        while (left >= 0) {
            if (right < leftLen || c.compare(arr[pos + left], arr[pos + right]) > 0) {
                Util.exch(arr, pos + (mergedPos--), pos + (left--));
            } else {
                Util.exch(arr, pos + (mergedPos--), pos + (right--));
            }
        }
        if (right != mergedPos) {
            while (right >= leftLen)
                Util.exch(arr, pos + (mergedPos--), pos + (right--));
        }
    }

    // returns the leftover length, then the leftover fragment
    private static <T> GrailState smartMergeWithBuffer(T[] arr, int pos, int leftOverLen, int leftOverFrag,
            int blockLen, Comparator<? super T> c) {
        int M = -blockLen;
        int left = 0;
        int right = leftOverLen;
        int leftEnd = right;
        int rightEnd = right + blockLen;
        int typeFrag = 1 - leftOverFrag; // 1 if inverted

        while (left < leftEnd && right < rightEnd) {
            if (c.compare(arr[pos + left], arr[pos + right]) - typeFrag < 0) {
                Util.exch(arr, pos + (M++), pos + (left++));
            } else
                Util.exch(arr, pos + (M++), pos + (right++));
        }

        int length;
        int fragment = leftOverFrag;

        if (left < leftEnd) {
            length = leftEnd - left;
            while (left < leftEnd) {
                Util.exch(arr, pos + (--leftEnd), pos + (--rightEnd));
            }
        } else {
            length = rightEnd - right;
            fragment = typeFrag;
        }
        return new GrailState(length, fragment);
    }

    // returns the leftover length, then the leftover fragment
    private static <T> GrailState smartMergeWithoutBuffer(T[] arr, int pos, int leftOverLen, int leftOverFrag,
            int regBlockLen, Comparator<? super T> c) {
        if (regBlockLen == 0) {
            return new GrailState(leftOverLen, leftOverFrag);
        }

        int len1 = leftOverLen;
        int len2 = regBlockLen;
        int typeFrag = 1 - leftOverFrag; // 1 if inverted

        if (len1 != 0 && c.compare(arr[pos + (len1 - 1)], arr[pos + len1]) - typeFrag >= 0) {
            while (len1 != 0) {
                int foundLen;
                // Binary search left, else search right
                if (typeFrag != 0) {
                    foundLen = binarySearch(arr, pos + len1, len2, arr[pos], true, c);
                } else {
                    foundLen = binarySearch(arr, pos + len1, len2, arr[pos], false, c);
                }

                if (foundLen != 0) {
                    rotate(arr, pos, len1, foundLen);
                    pos += foundLen;
                    len2 -= foundLen;
                }

                if (len2 == 0) {
                    return new GrailState(len1, leftOverFrag);
                }

                do {
                    pos++;
                    len1--;
                } while (len1 != 0 && c.compare(arr[pos], arr[pos + len1]) - typeFrag < 0);
            }
        }
        return new GrailState(len2, typeFrag);
    }

    //***** Sort With Extra Buffer *****//

    // arr[M..0) - free, arr[0, leftEnd)++arr[leftEnd,leftEnd+rightEnd-1)
    // -> arr[M, M + leftEnd + rightEnd)
    private static <T> void mergeLeftWithXBuf(T[] arr, int pos, int leftLength, int rightLength, int M,
            Comparator<? super T> c) {
        int left = 0;
        int right = leftLength;
        rightLength += leftLength;

        while (right < rightLength) {
            if (left == leftLength || c.compare(arr[pos + left], arr[pos + right]) > 0) {
                arr[pos + (M++)] = arr[pos + (right++)];
            } else
                arr[pos + (M++)] = arr[pos + (left++)];
        }
        if (M != left) {
            while (left < leftLength) {
                arr[pos + (M++)] = arr[pos + (left++)];
            }
        }
    }

    // returns the leftover length, then the leftover fragment
    private static <T> GrailState smartMergeWithXBuf(T[] arr, int pos, int leftOverLen, int leftOverFrag,
            int blockLen, Comparator<? super T> c) {
        int M = -blockLen;
        int left = 0;
        int right = leftOverLen;
        int leftEnd = right;
        int rightEnd = right + blockLen;
        int typeFrag = 1 - leftOverFrag; // 1 if inverted

        while (left < leftEnd && right < rightEnd) {
            if (c.compare(arr[pos + left], arr[pos + right]) - typeFrag < 0) {
                arr[pos + (M++)] = arr[pos + (left++)];
            } else {
                arr[pos + (M++)] = arr[pos + (right++)];
            }
        }

        int length;
        int fragment = leftOverFrag;

        if (left < leftEnd) {
            length = leftEnd - left;
            while (left < leftEnd) {
                arr[pos + (--rightEnd)] = arr[pos + (--leftEnd)];
            }
        } else {
            length = rightEnd - right;
            fragment = typeFrag;
        }
        return new GrailState(length, fragment);
    }

    /**
     *
     * @param arr         arr - starting array. arr[-lblock..-1] - buffer (if
     *                    havebuf).
     * @param midKey      key < midkey means stream A
     * @param pos         initial position
     * @param blockCount  length of regular blocks. First nblocks are stable sorted
     *                    by 1st elements and key-coded
     * @param aBlockCount number of regular blocks from stream A
     * @param lastLen     length of last (irregular) block from stream B, that
     *                    should go before nblock2 blocks. requires nblock2=0 (no
     *                    irregular blocks). llast>0, nblock2=0 is possible.
     */
    private static <T> void mergeBuffersLeftWithXBuf(T[] arr, int midKey, int pos, int blockCount, int regBlockLen,
            int aBlockCount, int lastLen, Comparator<? super T> c) {

        if (blockCount == 0) {
            int aBlocksLen = aBlockCount * regBlockLen;
            mergeLeftWithXBuf(arr, pos, aBlocksLen, lastLen, -regBlockLen, c);
            return;
        }

        int leftOverLen = regBlockLen;
        int processIndex = regBlockLen;
        int leftOverFrag = c.compare(arr[0], arr[midKey]) < 0 ? 0 : 1;
        int restToProcess;
        for (int keyIndex = 1; keyIndex < blockCount; keyIndex++, processIndex += regBlockLen) {
            restToProcess = processIndex - leftOverLen;
            int nextFrag = c.compare(arr[keyIndex], arr[midKey]) < 0 ? 0 : 1;

            if (nextFrag == leftOverFrag) {
                System.arraycopy(arr, pos + restToProcess, arr, pos + restToProcess - regBlockLen, leftOverLen);
                leftOverLen = regBlockLen;
            } else {
                GrailState results = smartMergeWithXBuf(arr, pos + restToProcess, leftOverLen, leftOverFrag,
                        regBlockLen, c);
                leftOverLen = results.getLeftOverLen();
                leftOverFrag = results.getLeftOverFrag();
            }
        }
        restToProcess = processIndex - leftOverLen;

        if (lastLen != 0) {
            if (leftOverFrag != 0) {
                System.arraycopy(arr, pos + restToProcess, arr, pos + restToProcess - regBlockLen, leftOverLen);
                restToProcess = processIndex;
                leftOverLen = regBlockLen * aBlockCount;
            } else {
                leftOverLen += regBlockLen * aBlockCount;
            }
            mergeLeftWithXBuf(arr, pos + restToProcess, leftOverLen, lastLen, -regBlockLen, c);
        } else {
            System.arraycopy(arr, pos + restToProcess, arr, pos + restToProcess - regBlockLen, leftOverLen);
        }
    }

    //***** End Sort With Extra Buffer *****//

    /**
     * build blocks of length {@code buildLen}. Input: [-buildLen, -1] elements are
     * buffer. Output: first buildLen elements are buffer, blocks 2 * buildLen and
     * last sub-block sorted.
     */
    private static <T> void buildBlocks(T[] arr, int pos, int len, int buildLen, T[] extBuf,
                                             int extBufLen, Comparator<? super T> c) {
        int buildBuf = Math.min(buildLen, extBufLen);

        while ((buildBuf & (buildBuf - 1)) != 0) {
            buildBuf &= buildBuf - 1; // max power or 2 - just in case
        }

        int extraM;
        int part;
        if (buildBuf != 0) {
            System.arraycopy(arr, pos - buildBuf, extBuf, 0, buildBuf);
            for (int M = 1; M < len; M += 2) {
                extraM = 0;
                if (c.compare(arr[pos + (M - 1)], arr[pos + M]) > 0) {
                    extraM = 1;
                }
                arr[pos + M - 3] = arr[pos + M - 1 + extraM];
                arr[pos + M - 2] = arr[pos + M - extraM];
            }
            if (len % 2 != 0) {
                arr[pos + len - 3] = arr[pos + len - 1];
            }

            pos -= 2;

            for (part = 2; part < buildBuf; part *= 2) {
                int left = 0;
                int right = len - 2 * part;

                while (left <= right) {
                    mergeLeftWithXBuf(arr, pos + left, part, part, -part, c);
                    left += 2 * part;
                }
                int rest = len - left;
                if (rest > part)
                    mergeLeftWithXBuf(arr, pos + left, part, rest - part, -part, c);
                else {
                    for (; left < len; left++) {
                        arr[pos + left - part] = arr[pos + left];
                    }
                }
                pos -= part;
            }
            System.arraycopy(extBuf, 0, arr, pos + len, buildBuf);
        } else {
            for (int M = 1; M < len; M += 2) {
                extraM = 0;
                if (c.compare(arr[pos + (M - 1)], arr[pos + M]) > 0) {
                    extraM = 1;
                }
                Util.exch(arr, pos + (M - 3), pos + (M - 1 + extraM));
                Util.exch(arr, pos + (M - 2), pos + (M - extraM));
            }
            if (len % 2 != 0)
                Util.exch(arr, pos + (len - 1), pos + (len - 3));
            pos -= 2;
            part = 2;
        }
        for (; part < buildLen; part <<= 1) {
            int left = 0;
            int right = len - 2 * part;

            while (left <= right) {
                mergeLeft(arr, pos + left, part, part, -part, c);
                left += 2 * part;
            }
            int rest = len - left;
            if (rest > part) {
                mergeLeft(arr, pos + left, part, rest - part, -part, c);
            } else {
                rotate(arr, pos + left - part, part, rest);
            }
            pos -= part;
        }
        int restToBuild = len % (2 * buildLen);
        int leftOverPos = len - restToBuild;
        if (restToBuild <= buildLen) {
            rotate(arr, pos + leftOverPos, restToBuild, buildLen);
        } else {
            mergeRight(arr, pos + leftOverPos, buildLen, restToBuild - buildLen, buildLen, c);
        }
        while (leftOverPos > 0) {
            leftOverPos -= 2 * buildLen;
            mergeRight(arr, pos + leftOverPos, buildLen, buildLen, buildLen, c);
        }
    }

    /**
     * @param arr         starting array. arr[-blockLen..-1] - buffer (if haveBuf).
     * @param blockLen    length of regular blocks. First blockCount blocks are
     *                    stable
     * @param aBlockCount regular blocks from stream A, aBlockCount = 0 is possible.
     * @param lastLen     length of last (irregular) block from stream B, that
     *                    should go before aBlockCount blocks.
     * @param c           Comparator
     * @param <T>         type to be compared by Comparator
     */
    private static <T> void mergeBuffersLeft(T[] arr, int midKey, int pos, int blockCount, int blockLen,
            boolean haveBuf, int aBlockCount, int lastLen, Comparator<? super T> c) {

        if (blockCount == 0) {
            int aBlocksLen = aBlockCount * blockLen;
            if (haveBuf) {
                mergeLeft(arr, pos, aBlocksLen, lastLen, -blockLen, c);
            } else {
                mergeWithoutBuffer(arr, pos, aBlocksLen, lastLen, c);
            }
            return;
        }

        int leftOverLen = blockLen;
        int processIndex = blockLen;
        int leftOverFrag = c.compare(arr[0], arr[midKey]) < 0 ? 0 : 1;
        int restToProcess;

        for (int keyIndex = 1; keyIndex < blockCount; keyIndex++, processIndex += blockLen) {
            restToProcess = processIndex - leftOverLen;
            int nextFrag = c.compare(arr[keyIndex], arr[midKey]) < 0 ? 0 : 1;
            if (nextFrag == leftOverFrag) {
                if (haveBuf) {
                    multiSwap(arr, pos + restToProcess - blockLen, pos + restToProcess, leftOverLen);
                }
                leftOverLen = blockLen;
            } else {
                GrailState results;
                if (haveBuf) {
                    results = smartMergeWithBuffer(arr, pos + restToProcess, leftOverLen, leftOverFrag, blockLen, c);
                } else {
                    results = smartMergeWithoutBuffer(arr, pos + restToProcess, leftOverLen, leftOverFrag, blockLen, c);
                }
                leftOverLen = results.getLeftOverLen();
                leftOverFrag = results.getLeftOverFrag();
            }
        }
        restToProcess = processIndex - leftOverLen;
        if (lastLen != 0) {
            if (leftOverFrag != 0) {
                if (haveBuf) {
                    multiSwap(arr, pos + restToProcess - blockLen, pos + restToProcess, leftOverLen);
                }
                restToProcess = processIndex;
                leftOverLen = blockLen * aBlockCount;
            } else {
                leftOverLen += blockLen * aBlockCount;
            }
            if (haveBuf) {
                mergeLeft(arr, pos + restToProcess, leftOverLen, lastLen, -blockLen, c);
            } else {
                mergeWithoutBuffer(arr, pos + restToProcess, leftOverLen, lastLen, c);
            }
        } else if (haveBuf) {
            multiSwap(arr, pos + restToProcess, pos + (restToProcess - blockLen), leftOverLen);
        }
    }

    private static <T> void lazyStableSort(T[] arr, int len, Comparator<? super T> c) {
        for (int M = 1; M < len; M += 2) {
            if (c.compare(arr[M - 1], arr[M]) > 0) {
                Util.exch(arr, (M - 1), M);
            }
        }

        for (int part = 2; part < len; part *= 2) {
            int left = 0;
            int right = len - 2 * part;
            while (left <= right) {
                mergeWithoutBuffer(arr, left, part, part, c);
                left += 2 * part;
            }
            int rest = len - left;
            if (rest > part) {
                mergeWithoutBuffer(arr, left, part, rest - part, c);
            }
        }
    }

    /**
     * keys are on the left of arr. Blocks of length buildLen combined. We'll
     * combine them into pairs buildLen and keys are powers of 2. (2 * buildLen /
     * regBlockLen) keys are guaranteed
     */
    private static <T> void combineBlocks(T[] arr, int pos, int len, int buildLen, int regBlockLen,
            boolean haveBuf, T[] buffer, Comparator<? super T> c) {

        int combineLen = len / (2 * buildLen);
        int leftOver = len % (2 * buildLen);

        if (leftOver <= buildLen) {
            len -= leftOver;
            leftOver = 0;
        }

        if (buffer != null) {
            System.arraycopy(arr, pos - regBlockLen, buffer, 0, regBlockLen);
        }
        for (int i = 0; i <= combineLen; i++) {
            if (i == combineLen && leftOver == 0) {
                break;
            }
            int blockPos = pos + i * 2 * buildLen;
            int blockCount = (i == combineLen ? leftOver : 2 * buildLen) / regBlockLen;

            binaryInsertSort(arr, blockCount + (i == combineLen ? 1 : 0), c);
            int midkey = buildLen / regBlockLen;
            for (int index = 1; index < blockCount; index++) {
                int leftIndex = index - 1;
                for (int rightIndex = index; rightIndex < blockCount; rightIndex++) {
                    int rightComp = c.compare(arr[blockPos + leftIndex * regBlockLen], arr[blockPos + rightIndex * regBlockLen]);
                    if (rightComp > 0 || (rightComp == 0 && c.compare(arr[leftIndex], arr[rightIndex]) > 0)) {
                        leftIndex = rightIndex;
                    }
                }
                if (leftIndex != index - 1) {
                    multiSwap(arr, blockPos + (index - 1) * regBlockLen, blockPos + leftIndex * regBlockLen, regBlockLen);
                    Util.exch(arr, (index - 1), leftIndex);
                    if (midkey == index - 1 || midkey == leftIndex) {
                        midkey ^= (index - 1) ^ leftIndex;
                    }
                }
            }
            int aBlockCount = 0;
            int lastLen = 0;
            if (i == combineLen) {
                lastLen = leftOver % regBlockLen;
            }
            if (lastLen != 0) {
                while (aBlockCount < blockCount && c.compare(arr[blockPos + blockCount * regBlockLen],
                        arr[blockPos + (blockCount - aBlockCount - 1) * regBlockLen]) < 0) {
                    aBlockCount++;
                }
            }
            if (buffer != null) {
                mergeBuffersLeftWithXBuf(arr, midkey, blockPos, blockCount - aBlockCount, regBlockLen, aBlockCount, lastLen, c);
            } else
                mergeBuffersLeft(arr, midkey, blockPos, blockCount - aBlockCount, regBlockLen, haveBuf, aBlockCount, lastLen, c);
        }
        if (buffer != null) {
            for (int p = len; --p >= 0; ) {
                arr[pos + p] = arr[pos + p - regBlockLen];
            }
            System.arraycopy(buffer, 0, arr, pos - regBlockLen, regBlockLen);
        } else if (haveBuf) {
            while (--len >= 0) {
                Util.exch(arr, pos + len, pos + len - regBlockLen);
            }
        }
    }

    private static <T> void grailSort(T[] arr, int len, T[] buffer, int bufferLen, Comparator<? super T> c) {
        if (len <= 32) {
            binaryInsertSort(arr, len, c);
            return;
        }

        int blockLen = 1;
        while (blockLen * blockLen < len) {
            blockLen *= 2;
        }
        int numKeys = ((len - 1) / blockLen) + 1;
        int keyLength = numKeys + blockLen;
        int keysFound = getKeys(arr, len, keyLength, c);
        boolean bufferEnabled = true;
        if (keysFound < keyLength) {
            if (keysFound < 4) {
                lazyStableSort(arr, len, c);
                return;
            }
            numKeys = blockLen;
            while (numKeys > keysFound) {
                numKeys /= 2;
            }
            bufferEnabled = false;
            blockLen = 0;
        }

        int M = blockLen + numKeys;
        int buildLen = bufferEnabled ? blockLen : numKeys;
        if (bufferEnabled) {
            buildBlocks(arr, M, len - M, buildLen, buffer, bufferLen, c);
        } else {
            buildBlocks(arr, M, len - M, buildLen, null, 0, c);
        }

        // 2 * buildLen are built
        while (len - M > (buildLen *= 2)) {
            int regBlockLen = blockLen;
            boolean buildBufEnabled = bufferEnabled;
            if (!bufferEnabled) {
                if (numKeys > 4 && numKeys / 8 * numKeys >= buildLen) {
                    regBlockLen = numKeys / 2;
                    buildBufEnabled = true;
                } else {
                    int calcKeys = 1;
                    int i = buildLen * keysFound / 2;
                    while (calcKeys < numKeys && i != 0) {
                        calcKeys *= 2;
                        i /= 8;
                    }
                    regBlockLen = (2 * buildLen) / calcKeys;
                }
            }
            combineBlocks(arr, M, len - M, buildLen, regBlockLen, buildBufEnabled,
                    buildBufEnabled && regBlockLen <= bufferLen ? buffer : null, c);
        }
        binaryInsertSort(arr, M, c);
        mergeWithoutBuffer(arr, 0, M, len - M, c);
    }

    public static <T> void sortWithoutBuffer(T[] arr, Comparator<? super T> c) {
        grailSort(arr, arr.length, null, 0, c);
    }

    public static <T> void sortWithBuffer(T[] arr, Comparator<? super T> c) {
        @SuppressWarnings("unchecked")
        T[] ExtBuf = (T[]) Array.newInstance(arr.getClass().getComponentType(), GRAIL_STATIC_BUFFER_LEN);
        grailSort(arr, arr.length, ExtBuf, GRAIL_STATIC_BUFFER_LEN, c);
    }

    public static <T> void sortWithDynBuffer(T[] arr, Comparator<? super T> c) {
        int tempLen = 1;
        while (tempLen * tempLen < arr.length) {
            tempLen *= 2;
        }
        @SuppressWarnings("unchecked")
        T[] ExtBuf = (T[]) Array.newInstance(arr.getClass().getComponentType(), tempLen);
        grailSort(arr, arr.length, ExtBuf, tempLen, c);
    }

    public static <T extends Comparable<? super T>> void sortWithoutBuffer(T[] arr) {
        class c implements Comparator<T> {
            @Override
            public int compare(T v, T w) {
                return v.compareTo(w);
            }
        }
        sortWithoutBuffer(arr, new c());
    }

    public static <T extends Comparable<? super T>> void sortWithBuffer(T[] arr) {
        class c implements Comparator<T> {
            @Override
            public int compare(T v, T w) {
                return v.compareTo(w);
            }
        }
        sortWithBuffer(arr, new c());
    }

    public static <T extends Comparable<? super T>> void sortWithDynBuffer(T[] arr) {
        class c implements Comparator<T> {
            @Override
            public int compare(T v, T w) {
                return v.compareTo(w);
            }
        }
        sortWithDynBuffer(arr, new c());
    }
}
