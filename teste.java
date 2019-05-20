import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class teste {

	public static Sample read(String f){
		Sample Ax = new Sample();
		try {
			FileReader fileReader = new FileReader(f);
			Scanner sc=new Scanner(fileReader);
			while(sc.hasNext()){
				String line=sc.next();
				String[] linesplit = line.split(",");
				ArrayList<Integer> a = new ArrayList<Integer>();
				for(int j=0; j < linesplit.length; j++)
					a.add(Integer.parseInt(linesplit[j]));
				Ax.add(a);}
			fileReader.close();
			sc.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Ax;
	}

	public static void writeO(Sample A, String f){
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(fos);
			oos.writeObject(A);
			fos.close();
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Sample readO(String f){
		Sample B = new Sample();
		try {
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			B = (Sample) ois.readObject();
			fis.close();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return B;
	}

}