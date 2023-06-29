package projectEncryption;

import java.util.LinkedList;

public abstract class Algorithem  implements AlgorithemModel
{
    public void setKeys(LinkedList<Integer> keys) {
        this.keys = keys;
    }

    private LinkedList<Integer> keys;//contains the keys of the encryption
    //constructor for the abstract class
    public Algorithem()
    {
        keys = new LinkedList<>();
    }
    public LinkedList<Integer> getKeys()
    {
        return this.keys;
    }
    //move Bits by the key given
    protected int MoveBites(int n,int key)
    {
        return n ^ (1 << key);
    }


}
