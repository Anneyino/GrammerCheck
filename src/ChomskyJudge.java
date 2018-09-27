import java.util.*;

public class ChomskyJudge {

	//首先，产生式左部如果不含非终结符，则语法格式错误
	private static boolean GrammerMistake1(ArrayList<String> LeftPart,ArrayList<String> VtSet) {
		boolean res = true;
		for(String l : LeftPart) {
			int VtNum=0;
			for(int i=0;i<l.length();i++) {
				if(VtSet.contains(String.valueOf(l.charAt(i)))){
					VtNum++;
				}
			}
			if(VtNum==l.length()) {
				res = false;
			}
		}
		return res;
	}
	//产生式的右部两个||符号之间必须包含ε，Vn，Vt中的至少一个。
	private static boolean GrammerMistake2(ArrayList<String> RightList) {
		boolean res = true;
		for(String r: RightList) {
			if(r.length()==0){
				res=false;
			}
		}
		return res;
	}
	
	//检测产生式左部是否均为单个非终结符，如果是，就为2型文法或3型文法，否则为0型文法或1型文法
	private static boolean FirstCheck(ArrayList<String> LeftPart, ArrayList<String> VnSet) {
		boolean res = true;
		for(String l : LeftPart) {
			if(!VnSet.contains(l)) {
				res = false;
			}
		}
		return res;
	}
	
	//判断是否为3型文法，包括左线性和右线性，返回true则为3型文法，返回false则为2型文法
	private static boolean SecondCheck(ArrayList<String> RightList,ArrayList<String> VtList,ArrayList<String> VnList) {
		boolean res =true;
		int leftNum = 0;
		int rightNum = 0;
		for(String r : RightList) {
			if(r.length()==1&&VtList.contains(r)){
				
			}else if(r.length()==2) {
				String s1 = String.valueOf(r.charAt(0));
				String s2 = String.valueOf(r.charAt(1));
				if(VtList.contains(s1)&&VnList.contains(s2)) {
					leftNum++;
				}else if(VtList.contains(s2)&&VnList.contains(s1)) {
					rightNum++;
				}else {
					res = false;
				}
			}else {
				res = false;
			}
		}
		if(leftNum!=0&&rightNum!=0) {
			res = false;
		}
			return res;
	}
	//判断区分1型文法和0型文法，若返回true则是1型文法，若返回false则为0型文法
	private static boolean ThirdCheck(int [] LeftLength,String [][] RightStringArr) {
		boolean res = true;
		for(int i =0;i<LeftLength.length;i++) {
			for(int j = 0;j<RightStringArr[i].length;j++) {
				if(LeftLength[i]>RightStringArr[i][j].length()&&RightStringArr[i][j]!="ε") {
					res=false;
				}
			}
		}
		return res;
	}
	//ArrayList去重函数
	private static void removeDuplicateWithOrder(ArrayList<String> List) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (String s: List) {
			if (set.add(s)) {
				newList.add(s);
			}
		}
		List.clear();
		List.addAll(newList);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while(true) {
		boolean GrammerFlag = true;	
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入文法: ");
		String lan = sc.nextLine().replace(" ", "");
		System.out.println("请输入非终结符Vn: ");
		String Vn = sc.nextLine().replace(" ", "");;
		System.out.println("请输入产生式规则: ");
		String P = sc.nextLine().replace(" ", "");;
		
		//获取开始符
		String S = "";
		if(lan.contains("G[")) {
	     S = lan.substring(lan.indexOf("[")+1,lan.indexOf("]"));
		}else {
			GrammerFlag = false;
		}
		//获取非终结符数组
        String VnArr[] = Vn.split(",");
        ArrayList<String> VnList = new ArrayList<String>();
        for(int i = 0;i<VnArr.length;i++) {
        	VnList.add(VnArr[i]);
        }
        //分隔各个产生式
        String Plist[] = P.split(",");
        //将产生式分为两部分，左部和右部（要求一一对应）
        int []LeftLength =new int[Plist.length];
        String [][] RightStringArr = new String[Plist.length][];
        for(int i = 0;i<Plist.length;i++) {
        	RightStringArr[i]=Plist[i].substring(Plist[i].indexOf("=")+1).split("\\|");
        	LeftLength[i] = Plist[i].substring(0,Plist[i].indexOf(":")).length();
        }
        
        
        //将产生式分为两部分，左部和右部（不需一一对应）
        ArrayList<String> RightPart = new ArrayList<String>();
        ArrayList<String> VtList = new ArrayList<String>();
        String []Sentences;
        String Sentence ="";
        ArrayList<String> LeftList = new ArrayList<String>();
        ArrayList<String> RightList = new ArrayList<String>();
        //获取所有的产生式右部
        for(int i = 0; i<Plist.length;i++) {
        	RightPart.add(Plist[i].substring(Plist[i].indexOf("=")+1));
        }
        for(String s : RightPart) {
        	Sentence += s + "|";
        }
        //将产生式右部按|分割，再合并
        Sentences = Sentence.split("\\|");
        //将分割后的产生式右部集合放入RightList
        for(String s : Sentences) {
        	RightList.add(s);
        }
        String CharList = "";
        for(String s : Sentences) {
        	CharList += s;
        }
        
      //获取所有的产生式左部
        for(int i = 0; i<Plist.length;i++) {
        	LeftList.add(Plist[i].substring(0,Plist[i].indexOf(":")));
        }
        
        //将合并后的产生式右部再加上左部
        for(String s : LeftList) {
        	CharList += s;
        }
        //然后去掉非终结符，加入到终结符的ArrayList
        char[] Clist = CharList.toCharArray();
        for(char c : Clist) {
        	String key = String.valueOf(c);
        	if(!VnList.contains(key)) {
        		VtList.add(key);
        	}
        }
        
        //然而实际上，这里的VtList中是包含ε的，但为了方便判断3型文法，我们暂且忽略
        String VnString ="";
		String VtString ="";
        
        //接下来则是判断文法的过程
		if(GrammerFlag) {
		if(VnList.contains(S)) {
        if(GrammerMistake1(LeftList,VtList)) {
        if(GrammerMistake2(RightList)) {
        	int flag = 0;
        	if(VtList.contains("ε")) {
        	VtList.remove("ε");
        	flag = 1;
        	}
            removeDuplicateWithOrder(VtList);
            removeDuplicateWithOrder(VnList);
    		for(String vn : VnList) {
    			if(VnList.indexOf(vn)==0) {
    			VnString += vn;
    			}else {
    				VnString += ","+vn;
    			}
    		}
    		for(String vt : VtList) {
    			if(VtList.indexOf(vt)==0) {
    			VtString += vt;
    			}else {
    				VtString += ","+vt;
    			}
    		}
    		System.out.println("文法"+lan+"=({"+VnString+"},{"+VtString+"},P,"+S+")");
    		System.out.println("P:  "+P.replace(",", "\n    "));
    		if(flag==1) {
    		VtList.add("ε");
    		}
        if(FirstCheck(LeftList,VnList)) {
        	if(SecondCheck(RightList,VtList,VnList)) {
        		
        		System.out.println("该文法是Chomsky3型文法");
        		
        	}else {
        		
        		System.out.println("该文法是Chomsky2型文法");
        		
        	}
        }else {
        	if(ThirdCheck(LeftLength,RightStringArr)) {
        		if(!VtList.contains("ε")) {
        		System.out.println("该文法是Chomsky1型文法");
        		}else {
        			System.out.println("该文法是Chomsky0型文法");
        		}
        	}else {
       
        		System.out.println("该文法是Chomsky0型文法");
        	}
        }
        }else {
        	System.err.println("ERROR:产生式的右部两个||符号之间必须包含ε，Vn，Vt中的至少一个!");
        }
        }
        else {
        	System.err.println("ERROR:该文法结构有误，存在一个产生式左部不包含非终结符！");
        }
		}else {
			System.err.println("ERROR:文法的开始符必须是一个非终结符！");
		}
		}else {
			System.err.println("ERROR:请输入正确的文法形式例如G[S]");
		}
		System.out.println("输入Y继续判断，输入其他任意键退出程序···");
	    String trigger = sc.nextLine().toUpperCase();
	    
	    if(!trigger.equals("Y")) {
	    	break;
	    }
	}
  
   }
}

