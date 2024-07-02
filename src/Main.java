public class Main {
    public static void main(String[] args) {
        //Employee employee1 = new Employee("AAA", 32);
        //clearEmployee employee2 = new Employee("BBB", 45);
        //System.out.println(employee1.hashCode());2
        //System.out.println(employee2.hashCode());

        Hashmap<String, String> hashMap = new Hashmap<>(4);
        String addresult = hashMap.put("+545454654", "AAAAAAAAAAA");
        addresult = hashMap.put("+8787878", "BBBBBBB");
        addresult = hashMap.put("+21212100", "CCCCCC");
        addresult = hashMap.put("+87878789", "DDDDDDD");
        addresult = hashMap.put("+87878790", "EEEEEEE");
        addresult = hashMap.put("+87878791", "FFFFFFF");
        addresult = hashMap.put("+87878792", "GGGGGGG");

        System.out.println(hashMap);

        System.out.println();

        for (var entity : hashMap){
            System.out.println(entity);
        }

    }
}