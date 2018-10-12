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
import MSPSAlgo.Item;
import MSPSAlgo.Sequence;


public class MSPrefixSpan {
	
	public static void main(String arge[]) {
		MSPrefixSpan MSPS = new MSPrefixSpan();
		MSPS.execute();
	}
	
	
	Map<Integer, Double> MIS = getItemsMIS();

	ArrayList<Sequence> Seq = getSequences(MIS);
	ArrayList<Sequence> patterns = new ArrayList<Sequence>();

	double SDC;

	int size = Seq.size();

	Map<Integer, Item> items = getMapItems(Seq);

	Comparator mis_item = new Comparator<Item>() {

		public int compare(Item o1, Item o2) {
	
			if (o1.mis > o2.mis)
				return 1;
			if (o1.mis == o2.mis && o1.count > o2.count)
				return 1;
			if (o1.mis < o2.mis)
				return -1;
			return 0;
		}
	};

	Comparator item_count = new Comparator<Item>() {

		public int compare(Item o1, Item o2) {
			
			if (o1.count > o2.count)
				return 1;
			if (o1.count < o2.count)
				return -1;
			return 0;
		}
	};

	

	private Map<Integer, Item> getMapItems(ArrayList<Sequence> Seq) {
		LinkedList<Item> items = getItems(Seq);
		Map<Integer, Item> mapItems = new HashMap<Integer, Item>();
		Item item = null;
		Iterator<Item> itemItem = items.iterator();
		
		for(itemItem = items.iterator();itemItem.hasNext();) {
			item = itemItem.next();
			mapItems.put(item.key, item);
			
		}
		
		return mapItems;
	}
	
 
	private ArrayList<Sequence> getSequences(Map<Integer, Double> MIS) {
		
		ArrayList<Sequence> Seq = new ArrayList<Sequence>();
		Sequence seq = null;
		ArrayList<Integer> array = null;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("src/data/data-2.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String st = new String();
		Pattern p1 = Pattern.compile("[\\{][^\\}]*[\\}]");
		Pattern p2 = Pattern.compile("\\d{1,}");
		Matcher m1 = null;
		Matcher m2 = null;
		try {
			while ((st = in.readLine()) != null) {
				m1 = p1.matcher(st);
				seq = new Sequence();
				while (m1.find()) {
					array = new ArrayList<Integer>();
					m2 = p2.matcher(m1.group());
					while (m2.find()) {
						array.add(Integer.parseInt(m2.group()));
					}
					seq.sequence.add(array);
				}
				Seq.add(seq);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Seq;
	}


	private Map<Integer, Double> getItemsMIS() {
	
		Map<Integer, Double> MIS = new HashMap<Integer, Double>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("src/data/para2-1.txt"));
			String s = new String();
			Pattern pattern1 = Pattern.compile("\\(\\d{1,}");
			Pattern pattern2 = Pattern.compile("\\d{1,}\\.\\d{1,}");
			Pattern pattern3 = Pattern.compile("MIS.*");
			Pattern pattern4 = Pattern.compile("SDC.*");
			int key = 0;
			double mis = 0;
			Matcher match1 = null;

			while ((s = in.readLine()) != null) {
				match1 = pattern3.matcher(s);
				if (match1.matches()) {
					match1 = pattern1.matcher(s);
					if (match1.find())
						key = Integer.parseInt(match1.group().substring(1));
					match1 = pattern2.matcher(s);
					if (match1.find()) {
						mis = Double.parseDouble(match1.group());
						MIS.put(key, mis);
					}
				} else {
					match1 = pattern4.matcher(s);
					if (match1.matches()) {
						match1 = pattern2.matcher(s);
						if (match1.find())
							SDC = Double.parseDouble(match1.group());
					}
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return MIS;
	}


	private LinkedList<Item> getItems(ArrayList<Sequence> Sequence) {
		Iterator<Sequence> item1 = Sequence.iterator();
		Iterator<ArrayList<Integer>> item2 = null;
		Iterator<Integer> item3 = null;
		Map<Integer, Item> mapItems = new LinkedHashMap<Integer, Item>();
		LinkedList<Item> Items = new LinkedList<Item>();
		
		int Item = 0;
		double mis = 0;
		Item Item2 = null;
		ArrayList<Integer> Item3 = new ArrayList<Integer>();
		
		for(item1 = Sequence.iterator();item1.hasNext();)
		{
			item2 = item1.next().sequence.iterator();
			while (item2.hasNext()) {
				item3 = item2.next().iterator();
				while (item3.hasNext()) {
					Item = item3.next();
					if (!mapItems.containsKey(Item)) {
						if (MIS.get(Item) == null)
							mis = 0;
						else
							mis = MIS.get(Item);
						mapItems.put(Item, new Item(Item, mis, 1, false));
						Item3.add(Item);
				
					}
					else if (!Item3.contains(Item)) {
						Item3.add(Item);
						Item2 = mapItems.get(Item);
						Item2.count++;
					
					}
				}
				
			}
			Item3.clear();
			
		}
		
		Items.addAll(mapItems.values());
	
		return Items;
	}
	
	
	

	
	private void update(ArrayList<Sequence> Seq,ArrayList<Integer> delItems) {
		ArrayList<ArrayList<Integer>> sequence = null;
		Iterator<ArrayList<Integer>> itemsequence = null;
		Iterator<Sequence> iteratorSequence = Seq.iterator();
		ArrayList<Integer> subSequence = null;
		Iterator<Integer> itemnext = null;
		
		for(iteratorSequence = Seq.iterator();iteratorSequence.hasNext();) {
			
			sequence = iteratorSequence.next().sequence;
			itemsequence = sequence.iterator();
			
			for(itemsequence = sequence.iterator();itemsequence.hasNext();) {
				
				subSequence = itemsequence.next();
				itemnext = subSequence.iterator();
				for(itemnext = subSequence.iterator();itemnext.hasNext();)
				{
					
					if (delItems.contains(itemnext.next())) {
						itemnext.remove();
					}	
				}
				
				if(!subSequence.isEmpty()) {
					//System.out.println("Do Nothing");
			}
				else {
					itemsequence.remove();
					
				}
			
			if(!sequence.isEmpty()) {
				//System.out.println("Do Nothing");
		}
			else {
				iteratorSequence.remove();
				
			}
			
				
			}
			
		}
	}

	
	private ArrayList<Sequence> copy(ArrayList<Sequence> Sequence) {
		ArrayList<Sequence> itemSeq = new ArrayList<Sequence>();
		Iterator<Sequence> seq = Sequence.iterator();
		Sequence sequence = null;
		
		for(seq=Sequence.iterator();seq.hasNext();)
		{
			sequence = seq.next().copy();
			itemSeq.add(sequence);
			
		}
		
		
		
		return itemSeq;
	}

	
	
	
	
	private void construct_frequent_Projected(Sequence s, ArrayList<Sequence> S,LinkedList<Sequence> freqSequences,LinkedList<ArrayList<Sequence>> projected, Item item) {
        ArrayList<Integer> keys = new ArrayList<Integer>();
        LinkedList<Item> items = getItems(S);
        while (!items.isEmpty()) {
            if (items.getFirst().key == 0)
                items.removeFirst();
            else if ((double) items.getFirst().count / this.size < item.mis)
                items.removeFirst();
            else {
                keys.add(items.getFirst().key);
                items.removeFirst();
            }
        }
        Iterator<Sequence> itemSequence = null;
        Sequence seq = null;
        ArrayList<ArrayList<Integer>> sequence = null;
        ArrayList<Integer> prefix = null;
        ArrayList<Sequence> Seqarr = null;
        int key = 0;
        Item itemseq = null;
        Sequence freqS = null;
        for (int i = 0; i < keys.size(); i++) {
     
            Seqarr = new ArrayList<Sequence>();
            freqS = new Sequence(s);
            freqS.count = 0;
            prefix = new ArrayList<Integer>();
            key = keys.get(i);
            prefix.add(key);
            freqS.sequence.add(prefix);
            itemseq = this.items.get(key);
            if (itemseq.count > freqS.maxSup
                    || itemseq.count < freqS.minSup) {
                if (freqS.maxSup < itemseq.count)
                    freqS.maxSup = itemseq.count;
                else
                    freqS.minSup = itemseq.count;
                if ((double) (freqS.maxSup - freqS.minSup) / size > SDC)
                    continue;
            }
            itemSequence = S.iterator();
            for(itemSequence = S.iterator();itemSequence.hasNext();) {
                sequence = itemSequence.next().sequenceProj(prefix);
                if (sequence != null) {
                    freqS.count++;
                    if (!sequence.isEmpty()) {
                        seq = new Sequence(sequence);
                        Seqarr.add(seq);
                    }
                }
                
            }
            
            if ((double) freqS.count / this.size >= item.mis) {
                freqSequences.add(freqS);
                projected.add(Seqarr);
            }
            Sequence freqSeq = new Sequence(freqS);
            Seqarr = new ArrayList<Sequence>();
            freqS = new Sequence(s);
            freqS.count = 0;
            freqS.minSup = freqSeq.minSup;
            freqS.maxSup = freqSeq.maxSup;
            seq = new Sequence(s);
            prefix = seq.sequence.get(seq.sequence.size() - 1);
            prefix.add(key);
            ArrayList<Integer> arraylist = null;
            arraylist = freqS.sequence.remove(freqS.sequence.size() - 1);
            arraylist.add(key);
            freqS.sequence.add(arraylist);
            
            itemSequence = S.iterator();
           
            for(itemSequence = S.iterator();itemSequence.hasNext();)
            {
                sequence = itemSequence.next().sequenceProj(prefix);
                if (sequence != null) {
                    freqS.count++;
                   
                    if(sequence.isEmpty()) {
                        //System.out.println("");
                        
                    }
                    else {
                        
                        seq = new Sequence(sequence);
                        Seqarr.add(seq);
                        
                    }
                }
                
            }
            if ((double) freqS.count / this.size >= item.mis) {
                freqSequences.add(freqS);
                projected.add(Seqarr);
            }
        }
        
    }
		
	

	
	private void recursive_PrefixSpan(LinkedList<ArrayList<Sequence>> projected,LinkedList<Sequence> frequentSequences, ArrayList<Sequence> patterns,Item item) {
	
		if (frequentSequences.isEmpty())
		{
			return;
		}
		else
		{
			//System.out.println("Continue");
		}
		ArrayList<Sequence> itemSequence = null;
		Sequence sequence = null;
		
		while (!frequentSequences.isEmpty()) {
			itemSequence = projected.poll();
			
			sequence = frequentSequences.poll();
			if (sequence.contain_item(item.key)) {
				patterns.add(sequence);
			}
			if (itemSequence.isEmpty()) {
				continue;
			}
			else {
				//break;
				
			}
			
			LinkedList<ArrayList<Sequence>> subProjected = new LinkedList<ArrayList<Sequence>>();
			LinkedList<Sequence> subfrequentSequences = new LinkedList<Sequence>();
			construct_frequent_Projected(sequence, itemSequence, subfrequentSequences, subProjected, item);
			recursive_PrefixSpan(subProjected, subfrequentSequences, patterns,
					item);
		}
		return;
	}

	

	private ArrayList<Sequence> PrefixSpan(Item item, ArrayList<Sequence> Sequence) {
	
		Sequence s = new Sequence(item);
		ArrayList<Sequence> patterns = new ArrayList<Sequence>();
		LinkedList<Sequence> frequentSequences = new LinkedList<Sequence>();
		frequentSequences.add(s);
		Iterator<Sequence> itemSequence = Sequence.iterator();
		
		for(itemSequence = Sequence.iterator();itemSequence.hasNext();)
		{
			s = itemSequence.next();
			
			if(s.contain_item(item.key)) {
				//System.out.println("Do Nothing");
			}
			else {
				itemSequence.remove();
				
			}
			
		}
	
		LinkedList<Item> items = getItems(Sequence);
		Collections.sort(items, item_count);
	//	System.out.println("item_countuenct"+comitem_count);
		Iterator<Item> itItem = items.iterator();
		Item inItem = null;
		ArrayList<Integer> deleteItems = new ArrayList<Integer>();
		for(itItem = items.iterator();itItem.hasNext();)
		{
			inItem = itItem.next();
			if (inItem.count < item.count
					&& (double) inItem.count / this.size < item.mis) {
				itItem.remove();
				deleteItems.add(inItem.key);
			} else if (inItem.key != item.key) {
				s = new Sequence(inItem);
				s.maxSup = this.items.get(inItem.key).count;
				s.minSup = s.maxSup;
				frequentSequences.add(s);
			}
			
		}
		update(Sequence, deleteItems);
		LinkedList<ArrayList<Sequence>> Projected_Seq = new LinkedList<ArrayList<Sequence>>();
		ArrayList<Sequence> itemSeq = null;
		Iterator<Sequence> itSequence2 = null;
		itemSequence = frequentSequences.iterator();
		
		ArrayList<ArrayList<Integer>> sequence1 = null;
		ArrayList<ArrayList<Integer>> sequence2 = null;
		
		for(itemSequence = frequentSequences.iterator();itemSequence.hasNext();)
		{
			sequence1 = itemSequence.next().sequence;
			itSequence2 = Sequence.iterator();
			itemSeq = new ArrayList<Sequence>();
			
			for(itSequence2 = Sequence.iterator();itSequence2.hasNext();)
			{
				s = itSequence2.next();
				sequence2 = s.sequenceProj(sequence1.get(0));
				if (sequence2 != null && !sequence2.isEmpty()) {
					if (sequence1.get(0).get(0) != item.key) {
						for (int i = 0; i < sequence2.size(); i++) {
							if (sequence2.get(i).contains(item.key)) {
								s = new Sequence(sequence2);
								itemSeq.add(s);
							}
						}
					} else {
						s = new Sequence(sequence2);
						itemSeq.add(s);
					}
				}
				
			}
			
			Projected_Seq.add(itemSeq);
			
		}
		
		recursive_PrefixSpan(Projected_Seq, frequentSequences, patterns, item);
		
		return patterns;
	}

	

	
	
	void execute() {
		LinkedList<Item> items = getItems(Seq);
		Collections.sort(items, mis_item);
		Iterator<Item> itemsItem = items.iterator();
		Item item1 = null;
		
		for(itemsItem = items.iterator();itemsItem.hasNext();)
		{
			item1 = itemsItem.next();
			if ((double) item1.count / this.size >= item1.mis) {
				item1.flag = true;
				//System.out.println(item1);
			}
			
		}
		item1 = null;
		Item item2 = null;
		ArrayList<Integer> delItems = new ArrayList<Integer>();
		ArrayList<Integer> delItemsSDC = new ArrayList<Integer>();
		itemsItem = items.iterator();
		
		for( itemsItem = items.iterator();itemsItem.hasNext();)
		{
			
			item1 = itemsItem.next();
			
			if (item1.flag == true) {
				
				for(itemsItem=items.iterator();itemsItem.hasNext();) {
					
					item2 = itemsItem.next();
					if ((double) item2.count / this.size < item1.mis) {
						delItems.add(item2.key);
						itemsItem.remove();
					} else if (Math.abs((double) (item2.count - item1.count)
									/ this.size) > SDC)
						delItemsSDC.add(item2.key);
					
				}
			
				
				if(delItems.isEmpty())
				{
					//System.out.println("Do Nothing");
				}
				else {
					update(Seq, delItems);
					delItems.clear();
					
				}
				ArrayList<Sequence> deleteItemSeq = null;
				deleteItemSeq = copy(Seq);
				
				if(delItemsSDC.isEmpty())
				{
					//System.out.println("Do Nothing");
				}
				else {
					update(deleteItemSeq, delItemsSDC);
					delItemsSDC.clear();
					
				}
				
				patterns.addAll(PrefixSpan(item1, deleteItemSeq));
				//System.out.println(patterns);
				delItems.add(item1.key);
				itemsItem = items.iterator();
				itemsItem.next();
				itemsItem.remove();
			} else {
				delItems.add(item1.key);
				itemsItem.remove();
			}
			
		}
	   
		printPatterns(patterns);
	}

	private void printPatterns(ArrayList<Sequence> patterns) {
		Map<Integer, List<Sequence>> orderedSeq = new HashMap<Integer, List<Sequence>>();
		for( Sequence sequence : patterns )
		{
			//System.out.println(patterns);
			int iSequenceLen = sequence.getLength();
			//System.out.println(iSequenceLen);
			List<Sequence> bin = null;
			if( orderedSeq.containsKey( iSequenceLen ) )
			{
				bin = orderedSeq.get( iSequenceLen );
				//System.out.println("bin"+bin);
			}
			if( bin == null )
			{
				bin = new ArrayList<Sequence>();
				orderedSeq.put( iSequenceLen, bin );
				//System.out.println("OrderedSeq"+orderedSeq);
			}
			
			bin.add( sequence );
			//System.out.println("bin2"+bin);
		}	

		
	
		StringBuilder masterStr = new StringBuilder();
		for( Map.Entry<Integer, List<Sequence>> kvp : orderedSeq.entrySet() )
		{
			if( masterStr.length() > 0 ) { masterStr.append( '\n' ); }
			
			masterStr.append( "The number of length " + kvp.getKey() + " sequential patterns is " + kvp.getValue().size() + "\n" );
			//System.out.println(kvp.getKey());
			for( Sequence s : kvp.getValue() )
			{
				masterStr.append( "Pattern:  " + s.toPattern() + "\n" );
			}
		}
		
		try {
			File f1 = new File("./output.txt");
			
			;
			if (f1.exists()) {
				f1.delete();
			}
			
			
			FileWriter fw2=null;
			fw2 = new FileWriter("./output.txt");
			
			BufferedWriter bw1 = new BufferedWriter(fw2);
			bw1.write(masterStr.toString());
			bw1.close();
			fw2.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}



	

}
