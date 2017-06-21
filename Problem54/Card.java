public class Card implements Comparable<Card>{

    private char value;
    private char suit;

    public Card(char v, char s)
    {
        value = v;
        suit  = s;
    }

    public int getValue()
    {
        switch(value)
        {
            case 'T': return 10;
            case 'J': return 11;
            case 'Q': return 12;
            case 'K': return 13;
            case 'A': return 14;
            default: return Character.getNumericValue(value);
        }
    }

    public char getSuit() { return suit; }

    @Override
    public int compareTo(Card other) {
        return this.getValue() - other.getValue();
    }
}
