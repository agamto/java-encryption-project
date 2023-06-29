package projectEncryption;

import java.io.File;
import java.util.LinkedList;

public interface AlgorithemModel
{
    //function to encrypt file
    String encrypt(File f);
    //function to decrypt file
    String decrypt(File f);
    // function to create keys
    default LinkedList<Integer> createKeys()
    {
        return null;
    }
}
