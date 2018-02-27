
import java.io.*;

public class Unshuffle
{
	public static void
	main (String[] inArgs)
	throws Exception
	{
		System.out.println ("reading minirom_v3.bin");
		
		FileInputStream	fis = new FileInputStream ("minirom_v3.bin");
		
		// this is one of Cary's temp files
		byte[]	buffer = new byte [65536];
		
		for (int i = 0; i < 8; i++)
		{
			fis.read (buffer);
			
			FileOutputStream	fos = new FileOutputStream ("temp" + (i + 1));
			
			try
			{
				fos.write (buffer);
			}
			finally
			{
				fos.close ();
			}
		}

		System.out.println ("writing ppg_wavetables_linear.bin");

		FileOutputStream	fos = new FileOutputStream ("ppg_wavetables_linear.bin");
		
		FileInputStream	temp1 = new FileInputStream ("temp1");
		FileInputStream	temp2 = new FileInputStream ("temp2");
		FileInputStream	temp3 = new FileInputStream ("temp3");
		FileInputStream	temp4 = new FileInputStream ("temp4");
		FileInputStream	temp5 = new FileInputStream ("temp5");
		FileInputStream	temp6 = new FileInputStream ("temp6");
		FileInputStream	temp7 = new FileInputStream ("temp7");
		FileInputStream	temp8 = new FileInputStream ("temp8");
		
		// as i calculate it, each discrete file is a wave
		buffer = new byte [256];
		
		// idk wtf Cary starts at 1 here
		for (int i = 1; i < 2049; i++)
		{
			if ((i % 128) <= 64 && (i % 128) != 0)
			{
				if (i % 4 == 1)
				{
					temp1.read (buffer);
				}
				else
				if (i % 4 == 2)
				{
					temp2.read (buffer);
				}
				else
				if (i % 4 == 3)
				{
					temp3.read (buffer);
				}
				else
				if (i % 4 == 0)
				{
					temp4.read (buffer);
				}

				fos.write (buffer);
			}

			if ((i % 128) >= 65 || (i % 128) == 0)
			{
				if (i % 4 == 1)
				{
					temp5.read (buffer);
				}
				else
				if (i % 4 == 2)
				{
					temp6.read (buffer);
				}
				else
				if (i % 4 == 3)
				{
					temp7.read (buffer);
				}
				else
				if (i % 4 == 0)
				{
					temp8.read (buffer);
				}

				fos.write (buffer);
			}
		}

		fos.close ();
		
		for (int i = 1; i <= 8; i++)
		{
			new File ("temp" + i).delete ();
		}
		
	}
	
}

