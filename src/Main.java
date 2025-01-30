import java.util.List;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the data you want to compress: ");
        String data = sc.nextLine();
        LZ77 lz = new LZ77();

        List<Tag> compressed = lz.compress(data);
        System.out.println("Compressed data:");
        for (Tag tag : compressed)
        {
            System.out.println("<" + tag.position + "," + tag.length + "," + tag.next_symbol + ">");
        }

        System.out.println("Decompressed data: " + lz.decompress(compressed));
    }
}
