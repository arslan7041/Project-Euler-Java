import java.io.FileReader;
import java.util.*;

public class Main {

    public static Scanner file;
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            file = new Scanner(new FileReader("poker.txt"));
        }
        catch(Exception x) {
            System.out.println(x.getMessage());
        }

        int p1_wins = 0;
        while(file.hasNextLine())
        {
            ArrayList<Card> hand1 = new ArrayList<>();
            ArrayList<Card> hand2 = new ArrayList<>();
            String cards[] = file.nextLine().split(" ");

            // fill arraylists with cards
            for(int i = 0; i < cards.length; i++)
            {
                if (i <= 4) hand1.add(new Card(cards[i].charAt(0), cards[i].charAt(1)));
                else hand2.add(new Card(cards[i].charAt(0), cards[i].charAt(1)));
            }

            Collections.sort(hand1);
            Collections.sort(hand2);

            // make Hand objects
            Hand p1 = new Hand(hand1);
            Hand p2 = new Hand(hand2);

            p1.setRank();
            p2.setRank();

            int p1_rank = p1.getRank();
            int p2_rank = p2.getRank();

            if( p1_rank > p2_rank) p1_wins++;
            if(p1_rank == p2_rank)
            {
                if(p1_rank != 2 && p1_rank != 6)
                {
                    if(p1.getRankValue().get(0) > p2.getRankValue().get(0)) p1_wins++;
                    if(p1.getRankValue().get(0) == p2.getRankValue().get(0))
                        p1_wins += high_card(hand1, hand2);
                }
                if(p1_rank == 2)
                {
                    if(p1.getRankValue().get(1) > p2.getRankValue().get(1))
                        p1_wins++;
                    if(p1.getRankValue().get(1) == p2.getRankValue().get(1))
                    {
                        if(p1.getRankValue().get(0) > p2.getRankValue().get(0))
                            p1_wins++;
                        if(p1.getRankValue().get(0) == p2.getRankValue().get(0))
                            p1_wins += high_card(hand1, hand2);
                    }
                }
                if(p1_rank == 6)
                {
                    if(p1.getRankValue().get(0) > p2.getRankValue().get(0))
                        p1_wins++;
                    if(p1.getRankValue().get(0) == p2.getRankValue().get(0))
                    {
                        if(p1.getRankValue().get(1) > p2.getRankValue().get(1))
                            p1_wins++;
                    }
                }
            }
        }

        System.out.println("Player 1 won " + (p1_wins-1) + " times");
    }

    // return 1 is hand1 has higher card, 0 otherwise
    public static int high_card(List<Card> hand1, List<Card> hand2)
    {
        for(int i = 4; i >= 0; i--)
            if(hand1.get(i).getValue() > hand2.get(i).getValue())
                return 1;

        return 0;
    }
}
