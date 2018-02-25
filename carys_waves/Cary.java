
import java.io.*;

/*

	file format, as i understand it, is...
	
	32 wavetables x 64 entries x 256 unsigned bytes per entry
*/

public class Cary
{
	public static void
	main (String[] inArgs)
	throws Exception
	{
		if (inArgs.length == 0)
		{
			System.err.println ("usage: java Cary filename");
			System.exit (1);
		}
		
		String	inputFileName = inArgs [0];
		File	inputFile = new File (inputFileName);
		
		int	expectedLength = 32 * 64 * 256;
		
		if (inputFile.length () != expectedLength)
		{
			System.err.println ("file isn't of expected length " + expectedLength);
			System.exit (1);
		}
		
		// now read the juicin' lot
		// note we convert to 16-bit signed integers immediately, woot
		
		int[]	buffer = new int [expectedLength];
		
		int	index = 0;
		FileInputStream	fis = new FileInputStream (inputFile);
		
		try
		{
			for (int i = 0; i < expectedLength; i++)
			{
				int	sample = fis.read ();
			
				// stupid Java thinks that bytes are signed
				// so convert from 8 bit unsigned to 16 bit signed
				sample &= 0xff;
			
				// convert to signed 16 bit
				sample -= 128;
				sample *= 256;
			
				buffer [i] = sample;
			}
		}
		finally
		{
			fis.close ();
		}
		
		fis = new FileInputStream ("template.wav");
		
		// read the meta blocks from the template and blow them out to the new wav
		byte[]	metaBuffer = new byte [44];
		
		fis.read (metaBuffer);
		fis.close ();

		// now we write 32 wavetable files
		// 16 byte samples * 256 samples per wave
		int	offset = 0;
		
		for (int table = 0; table < 32; table++)
		{
			int	tableOffset = table * (64 * 256);
			
			String	outputFileName = "ppg_wavetable_" + (table < 10 ? "0" : "") + table + ".wav";

			FileOutputStream	fos = new FileOutputStream (outputFileName);

			System.out.println ("writing " + outputFileName);
			
			try
			{
				fos.write (metaBuffer);

				for (int wave = 0; wave < 64; wave++)
				{
					int	waveOffset = wave * 256;

					if (inputFileName.equals ("ppg.bin"))
					{
						// note the 352 has 256 samples per wave just like the Miniwave
						// BUT Cary's waves are doubled, doubling the frequency
						// so we undouble and interpolate - poorly
						for (int sample = 0; sample < 128; sample++)
						{
							offset = tableOffset + waveOffset + sample;
				
							int	sampleData = buffer [offset];
				
							// little-endian
							fos.write (sampleData & 0xff);
							fos.write ((sampleData >> 8) & 0xff);
						
							int	nextSampleData = 0;
						
							if (sample < 127)
							{
								// interpolate with the next sample
								nextSampleData = buffer [offset + 1];
							}
							else
							{
								// interpolate with the zeroth sample
								nextSampleData = buffer [tableOffset + waveOffset];
							}
							
							sampleData = (sampleData + nextSampleData) / 2;

							// little-endian
							fos.write (sampleData & 0xff);
							fos.write ((sampleData >> 8) & 0xff);
						}
					}
					else
					{
						// if we are working with Cary's v3 file then he has undoubled and octaved down already
						for (int sample = 0; sample < 256; sample++)
						{
							offset = tableOffset + waveOffset + sample;
				
							int	sampleData = buffer [offset];
				
							// little-endian
							fos.write (sampleData & 0xff);
							fos.write ((sampleData >> 8) & 0xff);
						}
					}
				}
			}
			finally
			{
				fos.close ();
			}
		}

		
		
	}
	
}

