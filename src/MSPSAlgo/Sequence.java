package MSPSAlgo;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import MSPSAlgo.MSPrefixSpan;
import MSPSAlgo.Item;

public class Sequence {
	ArrayList<ArrayList<Integer>> sequence;
	double mis;

	int count;

	int maxSup;

	int minSup;
	
	public int getLength(){

		//System.out.println(sequence);
		//System.out.println(sequence.size());
		int sum=0;
		for(int i=0;i<sequence.size();i++) {
			//System.out.println(sequence.get(i).size());
			sum=sum+sequence.get(i).size();
			//System.out.println("Sum"+sum);
		
	}
		return sum;
	}
	
	public String toPattern()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append( '<' );
		for( int i = 0; i < sequence.size(); i++ )
		{
			//sb.append("{");
			//sb.replace(a,b,"{");
			sb.append( sequence.get(i).toString() );
			//sb.replace(i+1, i+2,"}");
			//sb.append('}');
		}
		
		sb.append( ">  Count:  " + count );
		String sq=sb.toString();
		sq = sq.replaceAll("sq","").replace('[','{').replace(']','}');
		//return sb.toString();
		return sq;
	}
	
	public ArrayList<Integer> subproject(ArrayList<Integer> subSequence, ArrayList<Integer> pre) {
		if (subSequence.size() < pre.size())
			return null;
		Iterator<Integer> iteItem = subSequence.iterator();
		ArrayList<Integer> SubSequencelist = new ArrayList<Integer>();
		
		for(iteItem = subSequence.iterator();iteItem.hasNext();) {
			
			SubSequencelist.add(iteItem.next());
			
		}
		for (int i = 0; i < pre.size();) {
		while (!SubSequencelist.isEmpty()) {
				
			if (pre.get(i) == SubSequencelist.get(0)) {
					i++;
					SubSequencelist.remove(0);
					break;
				} else {
					SubSequencelist.remove(0);
				}
			}
			if (i < pre.size() && SubSequencelist.isEmpty())
				return null;
		}
		
		if (SubSequencelist.isEmpty()) {
			//System.out.println("Do Nothing");
		}
		else {
			SubSequencelist.add(0, 0);
			
		}
		return SubSequencelist;
	}
	


	Sequence() {
		this.sequence = new ArrayList<ArrayList<Integer>>();
		this.mis = 0;
		this.count = 0;
		this.maxSup = 0;
		this.minSup = 0;
	}

	Sequence(Item item) {
		ArrayList<Integer> subSequence = new ArrayList<Integer>();
		this.sequence = new ArrayList<ArrayList<Integer>>();
		
		subSequence.add(item.key);
		//System.out.println("Item Key"+ item.key); 
	
		this.sequence.add(subSequence);
		this.mis = item.mis;
		this.count = item.count;
		this.maxSup = item.count;
		this.minSup = item.count;
	}

	Sequence(ArrayList<ArrayList<Integer>> sequence) {
		this.sequence = new ArrayList<ArrayList<Integer>>();
		Iterator<ArrayList<Integer>> itarr = null;
		Iterator<Integer> iterator = null;
		ArrayList<Integer> subSequence = null;
		itarr = sequence.iterator();
		for(itarr = sequence.iterator();itarr.hasNext();) {
			subSequence = new ArrayList<Integer>();
			iterator = itarr.next().iterator();
			while (iterator.hasNext()) {
				subSequence.add(iterator.next());
			}
			
			this.sequence.add(subSequence);
			
		}
		this.mis = 0;
		this.count = 0;
		this.maxSup = 0;
		this.minSup = 0;
	}

	Sequence(Sequence seq) {
		this.sequence = new Sequence(seq.sequence).sequence;
		this.mis = seq.mis;
		this.count = seq.count;
		this.maxSup = seq.maxSup;
		this.minSup = seq.minSup;
	}

	public Sequence copy() {
		Sequence seq = new Sequence();
		ArrayList<Integer> subSequence = null;
		Iterator<ArrayList<Integer>> items = null;
		Iterator<Integer> item = null;
		items = this.sequence.iterator();

		for(items = this.sequence.iterator();items.hasNext();) {
			subSequence = new ArrayList<Integer>();
			item = items.next().iterator();
			while (item.hasNext()) {
				subSequence.add(item.next());
			}
			
			seq.sequence.add(subSequence);
			
		}
		seq.mis = this.mis;
		seq.count = this.count;
		seq.maxSup = this.maxSup;
		seq.minSup = this.minSup;
		return seq;
	}

	public boolean contain_item(int item) {
		Iterator<ArrayList<Integer>> item1 = sequence.iterator();
		Iterator<Integer> item2 = null;
		for(item1 = sequence.iterator();item1.hasNext();)
		{
			item2 = item1.next().iterator();
			while (item2.hasNext()) {
				if (item2.next() == item)
					return true;
			}
			
		}
		return false;
	}

	
	public ArrayList<ArrayList<Integer>> sequenceProj(ArrayList<Integer> prefix) {
		
		ArrayList<Integer> itemSubSequence = null;
		ArrayList<ArrayList<Integer>> sequence = null;
		ArrayList<Integer> itItems = null;
		Iterator<ArrayList<Integer>> item = this.sequence.iterator();
		sequence = new Sequence(this.sequence).sequence;
		item = sequence.iterator();
		boolean seperate = (prefix.size() == 1) ? true : false;
		itItems = item.next();
		if (itItems.get(0) == 0) {
			if (!seperate) {
				itItems.remove(0);
				ArrayList<Integer> itemPrefix = new ArrayList<Integer>();
				itemPrefix.add(prefix.get(prefix.size() - 1));
				itemSubSequence = subproject(itItems, itemPrefix);
				item.remove();
				if (itemSubSequence != null) {
					if (itemSubSequence.isEmpty()) {
						return sequence;
					} else {
						sequence.add(0, itemSubSequence);
						return sequence;
					}
				}
			} 
			
			else
				item.remove();
		} else
			item = sequence.iterator();
		for(item = sequence.iterator();item.hasNext();) {
			
			itItems = item.next();
			itemSubSequence = subproject(itItems, prefix);
			item.remove();
			if (itemSubSequence != null) {
				if (itemSubSequence.isEmpty()) {
					return sequence;
				} else {
					sequence.add(0, itemSubSequence);
					return sequence;
				}
			}
			
		}
		return null;
	}

	

}
