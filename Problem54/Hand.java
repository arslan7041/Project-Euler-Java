import java.util.ArrayList;
import java.util.List;

public class Hand {

    private int rank;
    private List<Integer> rank_value;
    private ArrayList<Card> cards;

    public Hand(ArrayList<Card> c)
    {
        cards = c;
        rank_value = new ArrayList<Integer>();
        // rank_value needs to be size 2 to for two pairs and full houss
        rank_value.add(0);
        rank_value.add(0);
    }

    public int getRank() { return rank; }
    public List<Integer> getRankValue() { return rank_value; }

    public void setRank()
    {
        if(royal_flush()) rank = 9;
        else if(straight_flush()) rank = 8;
        else if(four_of_a_kind()) rank = 7;
        else if(full_house()) rank = 6;
        else if(flush()) rank = 5;
        else if(straight()) rank = 4;
        else if(three_of_a_kind()) rank = 3;
        else if(two_pairs()) rank = 2;
        else if(one_pair()) rank = 1;
        else rank_value.set(0, cards.get(cards.size()-1).getValue()); // rank = 0
    }

    private boolean royal_flush()
    {
        int val = 10;
        char s = cards.get(0).getSuit();
        for(Card c : cards)
        {
            if( c.getValue() != val ) return false;
            if( c.getSuit() != s) return false;
            val++;
        }

        rank_value.set(0, cards.get(cards.size()-1).getValue());
        return true;
    }

    // calls straight() and flush()
    private boolean straight_flush()
    {
        if(straight() && flush())
        {
            rank_value.set(0, cards.get(cards.size()-1).getValue());
            return true;
        }
        return false;
    }

    private boolean four_of_a_kind()
    {
        rank_value.set(0, 0);
        boolean found_four_of_a_kind = true;

        for(int i = 0; i <= 1; i++)
        {
            found_four_of_a_kind = true;
            rank_value.set(0, cards.get(i).getValue());
            for(int j = 0; j <= 3; j++)
            {
                if( cards.get(i + j).getValue() != rank_value.get(0) )
                {
                    found_four_of_a_kind = false;
                    break;
                }
            }

            if(found_four_of_a_kind)
                break;
        }

        return found_four_of_a_kind;
    }

    private boolean full_house()
    {
        boolean found_three_of_a_kind = true, found_pair = false;
        int pos_three_of_kind = 0;

        for(int i = 0; i <= 2; i++)
        {
            pos_three_of_kind = i;
            found_three_of_a_kind = true;
            rank_value.set(0, cards.get(i).getValue());
            for(int j = 0; j <= 2; j++)
            {
                if( cards.get(i + j).getValue() != rank_value.get(0) )
                {
                    found_three_of_a_kind = false;
                    break;
                }
            }

            if(found_three_of_a_kind)
                break;
        }

        if(!found_three_of_a_kind) return false;

        if(pos_three_of_kind == 2 && cards.get(0).getValue() == cards.get(1).getValue())
        {
            found_pair = true;
            rank_value.set(1, cards.get(0).getValue());
        }
        if(pos_three_of_kind == 0 && cards.get(3).getValue() == cards.get(4).getValue())
        {
            found_pair = true;
            rank_value.set(1, cards.get(3).getValue());
        }

        return found_pair;
    }

    private boolean flush()
    {
        char s = cards.get(0).getSuit();
        for(Card c : cards)
            if(c.getSuit() != s) return false;

        rank_value.set(0, cards.get(cards.size()-1).getValue());
        return true;
    }

    private boolean straight()
    {
        for(int i = 1; i < cards.size(); i++)
            if(cards.get(i).getValue() != cards.get(i-1).getValue()+1)
                return false;

        rank_value.set(0, cards.get(cards.size()-1).getValue());
        return true;
    }

    private boolean three_of_a_kind()
    {
        boolean found_three_of_a_kind = true;

        for(int i = 0; i <= 2; i++)
        {
            found_three_of_a_kind = true;
            rank_value.set(0, cards.get(i).getValue());
            for(int j = 0; j <= 2; j++)
            {
                if( cards.get(i + j).getValue() != rank_value.get(0) )
                {
                    found_three_of_a_kind = false;
                    break;
                }
            }

            if(found_three_of_a_kind)
                return true;
        }

        return false;
    }

    private boolean two_pairs()
    {
        boolean found_pair1 = true, found_pair2 = false;
        int pos_pair1 = 0;

        for(int i = 0; i <= 3; i++)
        {
            pos_pair1 = i;
            found_pair1 = true;
            rank_value.set(0, cards.get(i).getValue());
            for(int j = 0; j <= 1; j++)
            {
                if( cards.get(i + j).getValue() != rank_value.get(0) )
                {
                    found_pair1 = false;
                    break;
                }
            }

            if(found_pair1)
                break;
        }

        if(!found_pair1)
            return false;

        ArrayList<Card> cardsCopy = new ArrayList<Card>(cards);
        cardsCopy.remove(pos_pair1);
        cardsCopy.remove(pos_pair1);

        for(int i = 0; i <= 1; i++)
        {
            found_pair2 = true;
            rank_value.set(1, cardsCopy.get(i).getValue());
            for(int j = 0; j <= 1; j++)
            {
                if( cardsCopy.get(i + j).getValue() != rank_value.get(1) )
                {
                    found_pair2 = false;
                    break;
                }
            }

            if(found_pair2)
                break;
        }

        return found_pair2;
    }

    private boolean one_pair()
    {
        boolean found_pair = true;

        for(int i = 0; i <= 3; i++)
        {
            found_pair = true;
            rank_value.set(0, cards.get(i).getValue());
            for(int j = 0; j <= 1; j++)
            {
                if( cards.get(i + j).getValue() != rank_value.get(0) )
                {
                    found_pair = false;
                    break;
                }
            }

            if(found_pair)
                return true;
        }
        return false;
    }
}
