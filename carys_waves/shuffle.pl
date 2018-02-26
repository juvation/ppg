#!/usr/bin/perl -w

if( $ARGV[0] ) { $directory = $ARGV[0]; }
chdir $directory or die "Couldn't go to directory $directory: $!";

if( $ARGV[1] ) { $name = $ARGV[1]; }
else { die "No filename specified$!";}

print STDOUT "$0 running on $directory:\n";

@files2sort = <*>;

$j = 0;

foreach $file (sort @files2sort) {
	push @list, $file;
	$j = $j + 1;	
}

if ( $j < 2048 ) {
	die "Only $j files found, exiting.\n";
}

print STDOUT "$j #name\n";

system "rm -f temp*";

system "touch temp1 temp2 temp3 temp4 temp5 temp6 temp7 temp8";

$i = 0;

foreach $sample (sort @list) {

	$i = $i + 1;

	if ( $i % 128 <= 64 && $i % 128 != 0 ) {

		if ( $i % 4 == 1 ) {
			print STDOUT "$i $sample temp1\n";
			system "cat $sample >> temp1";
		}
		if ( $i % 4 == 2 ) {
			print STDOUT "$i $sample temp2\n";
			system "cat $sample >> temp2";
		}
		if ( $i % 4 == 3 ) {
			print STDOUT "$i $sample temp3\n";
			system "cat $sample >> temp3";
		}
		if ( $i % 4 == 0 ) {
			print STDOUT "$i $sample temp4\n";
			system "cat $sample >> temp4";
		}
	}

	if ( $i % 128 >= 65 || $i % 128 == 0 ) {

		if ( $i % 4 == 1 ) {
			print STDOUT "$i $sample temp5\n";
			system "cat $sample >> temp5";
		}
		if ( $i % 4 == 2 ) {
			print STDOUT "$i $sample temp6\n";
			system "cat $sample >> temp6";
		}
		if ( $i % 4 == 3 ) {
			print STDOUT "$i $sample temp7\n";
			system "cat $sample >> temp7";
		}
		if ( $i % 4 == 0 ) {
			print STDOUT "$i $sample temp8\n";
			system "cat $sample >> temp8";
		}
	}

}

print STDOUT "Writing file $name.bin ... ";

system "cat temp1 temp2 temp3 temp4 temp5 temp6 temp7 temp8 > ../$name.bin";

print STDOUT "Done!\n";

system "rm -f temp*";

