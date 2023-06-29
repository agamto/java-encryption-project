package projectEncryption;

import java.io.*;
import java.util.LinkedList;

public class Modulo10Encryption extends Algorithem
{
    public Modulo10Encryption()
    {
        super();
        createKeys();
    }

    @Override
    public String encrypt(File f) {
        try {


            int temp, index = 0, size = super.getKeys().size();
            FileOutputStream out;
            FileInputStream in;
            File outFile = new File(f.getParentFile(), "2" + f.getName());
            in = new FileInputStream(f);
            out = new FileOutputStream(outFile);
            while ((temp = in.read())!=-1)
            {
                out.write(super.MoveBites(temp,super.getKeys().get(index++)));
                if(size == index)
                    index = 0;
            }
            for (int i = 0 ; i< size;i++)
                out.write(super.getKeys().get(i));
            in.close();
            out.close();
            return outFile.getAbsolutePath();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return  "";
    }

    @Override
    public String decrypt(File f)
    {
        try
        {
            int temp, index = 0;
            int [] vec =  new int[61];
            FileOutputStream out;
            FileInputStream in;
            File outFile = new File(f.getParentFile(), f.getName().substring(1));
            in = new FileInputStream(f);
            out = new FileOutputStream(outFile);
            long sizeOfFile = f.length();
            in.skip(sizeOfFile - 61);
            while((temp=in.read() ) != -1)
            {
                vec[index++] = temp;
            }
            in = new FileInputStream(f);
            index = 0;
            while((temp = in.read()) != -1)
            {
                out.write(super.MoveBites(temp,vec[index]));
                index++;
                if(index == 61)
                index = 0;
            }
            out.getChannel().truncate(out.getChannel().size()-61);
            in.close();
            out.close();
            return  outFile.getAbsolutePath();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return "";
    }

    @Override
    public LinkedList<Integer> createKeys() {
            LinkedList<Integer> list = super.getKeys();
            long a = 0,b = 1,c ,i;
            list.add(0);
            list.add(1);
            for (i = 2;i<=60;i++)
            {
                c = a+b;
                a = b;
                b= c;
                if(c%10 == 9)
                    list.add((int)((c%10)-1));
                else
                    list.add((int)(c %10));
            }
            return list;
    }
}
