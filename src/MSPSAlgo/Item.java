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
import MSPSAlgo.Sequence;

public class Item {
	int key;
	ArrayList<ArrayList<Integer>> sequence;
	Map<Integer, Item> items;
	double mis;

	int count;

	boolean flag;

	Item() {
		this.key = 0;
		this.mis = 0;
		this.count = 0;
		this.flag = false;
	}

	Item(int arg0, double arg1, int arg2, boolean arg3) {
		this.key = arg0;
		this.mis = arg1;
		this.count = arg2;
		this.flag = arg3;
	}

	public Item copyItem() {
		Item item = new Item();
		item.flag = this.flag;
		item.count = this.count;
		item.flag = this.flag;
		item.key = this.key;
		return item;
	}

	public int getLength() {
		return items.size();
	}

}
