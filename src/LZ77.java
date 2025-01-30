import java.util.ArrayList;
import java.util.List;

class Tag
{
    int position;
    int length;
    char next_symbol;

    Tag(int position, int length, char next_symbol)
    {
        this.position = position;
        this.length = length;
        this.next_symbol = next_symbol;
    }
}

class LZ77
{
    int searchWindowSize = 10;
    int lookAheadWindowSize = 10;

    public List<Tag> compress(String data)
    {
        ArrayList<Tag> compressed = new ArrayList<>();
        int i = 0;

        while (i < data.length())
        {
            int maxLength = 0;
            int position = 0;
            char next_symbol;

            int windowStart = Math.max(0, i - searchWindowSize);
            int windowEnd = i;

            // Define maxLookAhead to limit the matching process within the look-ahead window size
            int maxLookAhead = Math.min(lookAheadWindowSize, data.length() - i);

            for (int j = windowStart; j < windowEnd; j++)
            {
                int len = 0;

                while (len < maxLookAhead &&
                        (i + len < data.length()) &&
                        (j + len < windowEnd) &&
                        (data.charAt(i + len) == data.charAt(j + len)))
                {
                    len++;
                }

                if (len > maxLength)
                {
                    maxLength = len;
                    position = i - j;
                }
            }

            // Get the next symbol after the match, or '\0' if end of data is reached
            next_symbol = (i + maxLength < data.length()) ? data.charAt(i + maxLength) : '\0';
            compressed.add(new Tag(position, maxLength, next_symbol));

            // Move to the next position in the data after the matched sequence
            i += maxLength + 1;
        }

        return compressed;
    }

    public String decompress(List<Tag> compressed)
    {
        StringBuilder decompressed = new StringBuilder();

        for (Tag tag : compressed)
        {
            int start = decompressed.length() - tag.position;

            for (int j = start; j < start + tag.length; j++)
            {
                decompressed.append(decompressed.charAt(j));
            }

            if (tag.next_symbol != '\0')
            {
                decompressed.append(tag.next_symbol);
            }
        }

        return decompressed.toString();
    }
}