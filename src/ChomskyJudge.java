import java.util.*;

public class ChomskyJudge {

	//���ȣ�����ʽ������������ս�������﷨��ʽ����
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
	//����ʽ���Ҳ�����||����֮���������ţ�Vn��Vt�е�����һ����
	private static boolean GrammerMistake2(ArrayList<String> RightList) {
		boolean res = true;
		for(String r: RightList) {
			if(r.length()==0){
				res=false;
			}
		}
		return res;
	}
	
	//������ʽ���Ƿ��Ϊ�������ս��������ǣ���Ϊ2���ķ���3���ķ�������Ϊ0���ķ���1���ķ�
	private static boolean FirstCheck(ArrayList<String> LeftPart, ArrayList<String> VnSet) {
		boolean res = true;
		for(String l : LeftPart) {
			if(!VnSet.contains(l)) {
				res = false;
			}
		}
		return res;
	}
	
	//�ж��Ƿ�Ϊ3���ķ������������Ժ������ԣ�����true��Ϊ3���ķ�������false��Ϊ2���ķ�
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
	//�ж�����1���ķ���0���ķ���������true����1���ķ���������false��Ϊ0���ķ�
	private static boolean ThirdCheck(int [] LeftLength,String [][] RightStringArr) {
		boolean res = true;
		for(int i =0;i<LeftLength.length;i++) {
			for(int j = 0;j<RightStringArr[i].length;j++) {
				if(LeftLength[i]>RightStringArr[i][j].length()&&RightStringArr[i][j]!="��") {
					res=false;
				}
			}
		}
		return res;
	}
	//ArrayListȥ�غ���
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
		System.out.println("�������ķ�: ");
		String lan = sc.nextLine().replace(" ", "");
		System.out.println("��������ս��Vn: ");
		String Vn = sc.nextLine().replace(" ", "");;
		System.out.println("���������ʽ����: ");
		String P = sc.nextLine().replace(" ", "");;
		
		//��ȡ��ʼ��
		String S = "";
		if(lan.contains("G[")) {
	     S = lan.substring(lan.indexOf("[")+1,lan.indexOf("]"));
		}else {
			GrammerFlag = false;
		}
		//��ȡ���ս������
        String VnArr[] = Vn.split(",");
        ArrayList<String> VnList = new ArrayList<String>();
        for(int i = 0;i<VnArr.length;i++) {
        	VnList.add(VnArr[i]);
        }
        //�ָ���������ʽ
        String Plist[] = P.split(",");
        //������ʽ��Ϊ�����֣��󲿺��Ҳ���Ҫ��һһ��Ӧ��
        int []LeftLength =new int[Plist.length];
        String [][] RightStringArr = new String[Plist.length][];
        for(int i = 0;i<Plist.length;i++) {
        	RightStringArr[i]=Plist[i].substring(Plist[i].indexOf("=")+1).split("\\|");
        	LeftLength[i] = Plist[i].substring(0,Plist[i].indexOf(":")).length();
        }
        
        
        //������ʽ��Ϊ�����֣��󲿺��Ҳ�������һһ��Ӧ��
        ArrayList<String> RightPart = new ArrayList<String>();
        ArrayList<String> VtList = new ArrayList<String>();
        String []Sentences;
        String Sentence ="";
        ArrayList<String> LeftList = new ArrayList<String>();
        ArrayList<String> RightList = new ArrayList<String>();
        //��ȡ���еĲ���ʽ�Ҳ�
        for(int i = 0; i<Plist.length;i++) {
        	RightPart.add(Plist[i].substring(Plist[i].indexOf("=")+1));
        }
        for(String s : RightPart) {
        	Sentence += s + "|";
        }
        //������ʽ�Ҳ���|�ָ�ٺϲ�
        Sentences = Sentence.split("\\|");
        //���ָ��Ĳ���ʽ�Ҳ����Ϸ���RightList
        for(String s : Sentences) {
        	RightList.add(s);
        }
        String CharList = "";
        for(String s : Sentences) {
        	CharList += s;
        }
        
      //��ȡ���еĲ���ʽ��
        for(int i = 0; i<Plist.length;i++) {
        	LeftList.add(Plist[i].substring(0,Plist[i].indexOf(":")));
        }
        
        //���ϲ���Ĳ���ʽ�Ҳ��ټ�����
        for(String s : LeftList) {
        	CharList += s;
        }
        //Ȼ��ȥ�����ս�������뵽�ս����ArrayList
        char[] Clist = CharList.toCharArray();
        for(char c : Clist) {
        	String key = String.valueOf(c);
        	if(!VnList.contains(key)) {
        		VtList.add(key);
        	}
        }
        
        //Ȼ��ʵ���ϣ������VtList���ǰ����ŵģ���Ϊ�˷����ж�3���ķ����������Һ���
        String VnString ="";
		String VtString ="";
        
        //�����������ж��ķ��Ĺ���
		if(GrammerFlag) {
		if(VnList.contains(S)) {
        if(GrammerMistake1(LeftList,VtList)) {
        if(GrammerMistake2(RightList)) {
        	int flag = 0;
        	if(VtList.contains("��")) {
        	VtList.remove("��");
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
    		System.out.println("�ķ�"+lan+"=({"+VnString+"},{"+VtString+"},P,"+S+")");
    		System.out.println("P:  "+P.replace(",", "\n    "));
    		if(flag==1) {
    		VtList.add("��");
    		}
        if(FirstCheck(LeftList,VnList)) {
        	if(SecondCheck(RightList,VtList,VnList)) {
        		
        		System.out.println("���ķ���Chomsky3���ķ�");
        		
        	}else {
        		
        		System.out.println("���ķ���Chomsky2���ķ�");
        		
        	}
        }else {
        	if(ThirdCheck(LeftLength,RightStringArr)) {
        		if(!VtList.contains("��")) {
        		System.out.println("���ķ���Chomsky1���ķ�");
        		}else {
        			System.out.println("���ķ���Chomsky0���ķ�");
        		}
        	}else {
       
        		System.out.println("���ķ���Chomsky0���ķ�");
        	}
        }
        }else {
        	System.err.println("ERROR:����ʽ���Ҳ�����||����֮���������ţ�Vn��Vt�е�����һ��!");
        }
        }
        else {
        	System.err.println("ERROR:���ķ��ṹ���󣬴���һ������ʽ�󲿲��������ս����");
        }
		}else {
			System.err.println("ERROR:�ķ��Ŀ�ʼ��������һ�����ս����");
		}
		}else {
			System.err.println("ERROR:��������ȷ���ķ���ʽ����G[S]");
		}
		System.out.println("����Y�����жϣ���������������˳����򡤡���");
	    String trigger = sc.nextLine().toUpperCase();
	    
	    if(!trigger.equals("Y")) {
	    	break;
	    }
	}
  
   }
}

