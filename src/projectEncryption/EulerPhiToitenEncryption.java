package projectEncryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedList;

public class EulerPhiToitenEncryption extends Algorithem
{
    public EulerPhiToitenEncryption()
    {
        super();
    }

    private int phi(int n)
    {
        float res =n;
       for (int p=2;p*p <= n;++p)
       {
           if(n%p == 0)
           {
               while (n%p == 0)
                   n/=p;
               res *=(1.0 - (1.0/(float)p));

           }
       }
       if(res > 1)
           res = n-1;
       return (int)(res);
    }
    private String CreateFile(File f,String s)
    {
        try
        {
            int temp,index = 1;
            FileInputStream in;
            FileOutputStream out;
            File outFile = new File(f.getParentFile(), s );
            in  = new FileInputStream(f);
            out = new FileOutputStream(outFile);
            while((temp = in.read()) != -1)
            {
                out.write(super.MoveBites(temp,phi(index++)));
            }

            out.close();
            in.close();
            return outFile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return "";
    }
    @Override
    public String encrypt(File f) {
       return CreateFile(f,"3"+ f.getName());
    }

    @Override
    public String decrypt(File f) {
       return CreateFile(f,f.getName().substring(1));
    }


}
