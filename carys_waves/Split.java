
import java.io.*;

public class Split
{
	public static void
	main (String[] inArgs)
	throws Exception
	{
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
	}
}
