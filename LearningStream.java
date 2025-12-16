import java.util.*;
import java.util.stream.Collectors;


public class LearningStream {
    public static void main(String[] args) {
        // Q1. Character Frequency
        // String s = "banana";
        // Map<Character,Long> mp = s.chars()
        //                                 .mapToObj(ch -> (char)ch)
        //                                 .collect(Collectors.groupingBy(
        //                                     ch->ch,
        //                                     Collectors.counting()
        //                                 ));

        // System.out.println(mp);


        // Q2. Word Frequency
        // List<String> words = List.of("apple", "banana", "apple", "orange", "banana", "apple");
		// Map<String, Long> mp =
		// 	words.stream()
		// 		.collect(Collectors.groupingBy(
		// 			word->word,
		// 			Collectors.counting()
		// 		));

		// System.out.println(mp);
        
        
        // Q3. Length of Each Word
        // List<String> words = List.of("java", "stream", "api");
        // Map<String, Integer> mp = 
        //     words.stream()
        //         .collect(Collectors.toMap(
        //                     s->s,
        //                     String::length
        //         ));

        // System.out.println(mp);

        // Q4. Group Words by Length
        // List<String> words = List.of("java", "is", "very", "powerful");
        // Map<Integer, List<String>> mp = 
        //     words.stream()
        //         .collect(Collectors.groupingBy(String::length));

        // System.out.println(mp);

        // Q5. Group Names by First Character
        // List<String> names = List.of("Alice", "Bob", "Charlie", "Anna", "Brian");
        // Map<Character, List<String>> mp = 
        //     names.stream().
        //         collect(Collectors.groupingBy(word -> word.charAt(0)));

        // System.out.println(mp);

        // Q6. Count Words by Length
        // List<String> words = List.of("java", "is", "fun", "and", "powerful"); // {2=1, 3=1, 4=3, 8=1}
        // Map<Integer, Long> mp = 
        //     words.stream()
        //         .collect(Collectors.groupingBy(
        //             str -> str.length(),
        //             Collectors.counting()
        //         ));

        // System.out.println(mp);

        
        // Q7. For Each Word, Count Character Frequency
        // List<String> words = List.of("apple", "banana");
        // /* 
        //     Output:
        //     {
        //         apple={a=1, p=2, l=1, e=1},
        //         banana={b=1, a=3, n=2}
        //     }
        // */
        // Map<String,Map<Character,Long>> mp = 
        //     words.stream()
        //         .collect(Collectors.toMap(
        //             word -> word,
        //             word -> word.chars()
        //                         .mapToObj(ch -> (char)ch)
        //                         .collect(Collectors.groupingBy(
        //                             ch -> ch,
        //                             Collectors.counting()
        //                         ))
        //         ));

        // System.out.println(mp);

        // Q8. Group Numbers by Even/Odd
        // List<Integer> nums = List.of(1,2,3,4,5,6,7);
        // /* 
        //     {
        //         true=[2,4,6],
        //         false=[1,3,5,7]
        //     }
        // */
        // Map<Boolean,List<Integer>> mp = nums.stream()
        //     .collect(Collectors.groupingBy(
        //         x -> x%2==0
        //     ));

        // System.out.println(mp);

        
        // Q9. For Each City, Count Repeated Characters Only
        // List<String> cities = List.of("Mumbai", "Chennai");
        // /* 
        //     {
        //         Mumbai={m=2},
        //         Chennai={n=2}
        //     }
        // */
        // Map<String,Map<Character,Long>> mp = 
        //     cities.stream()
        //         .collect(Collectors.toMap(
        //             word -> word,
        //             word -> word.toLowerCase()
        //                         .chars()
        //                         .mapToObj(ch -> (char) ch)
        //                         .collect(Collectors.groupingBy(
        //                             ch -> ch,
        //                             Collectors.counting()
        //                         ))
        //                         .entrySet()
        //                         .stream()
        //                         .filter(e -> e.getValue()>1)
        //                         .collect(Collectors.toMap(
        //                             Map.Entry::getKey,
        //                             Map.Entry::getValue
        //                         ))
        //         ));

        // System.out.println(mp);



        // Q10. Group Employees by Department and Count
        // List<Employee> employees = List.of(
        //     new Employee("Alice", "IT"),
        //     new Employee("Bob", "HR"),
        //     new Employee("Charlie", "IT"),
        //     new Employee("Diana", "Finance"),
        //     new Employee("Eve", "HR"),
        //     new Employee("Frank", "IT")
        // ); // Expected output:  {IT=3, HR=2, Finance=1}

        // Map<String, Long> mp = 
        //     employees.stream()
        //                 .collect(Collectors.groupingBy(
        //                     Employee::getDepartment,
        //                     Collectors.counting()
        //                 ));

        // System.out.println(mp);


        // Q11. Group Employees by Department → Names Only
        // List<Employee> employees = List.of(
        //     new Employee("Alice", "IT"),
        //     new Employee("Bob", "HR"),
        //     new Employee("Charlie", "IT"),
        //     new Employee("Diana", "Finance"),
        //     new Employee("Eve", "HR"),
        //     new Employee("Frank", "IT")
        // );
        // // {
        // //     IT=[Alice, Charlie],
        // //     HR=[Eve]
        // // }
        // Map<String,List<String>> mp = 
        //             employees.stream()
        //                     .collect(Collectors.groupingBy(
        //                         Employee::getDepartment,
        //                         Collectors.mapping(
        //                             Employee::getName,
        //                             Collectors.toList()
        //                         )
        //                     ));
                            
        
        // System.out.println(mp);

        // Q12. Find the Most Frequent Character in a String
        String s = "success";
        Map.Entry<Character, Long> result = 
                            s.chars()
                            .mapToObj(ch -> (char) ch)
                            .collect(Collectors.groupingBy(
                                ch -> ch,
                                Collectors.counting()
                            ))
                            .entrySet()
                            .stream()
                            .max(Map.Entry.comparingByValue())
                            .orElseThrow();

        Character ans = result.getKey();

        System.out.println(ans);


    }
}

// class Employee {
//     String name;
//     String department;

//     Employee(String name, String department) {
//         this.name = name;
//         this.department = department;
//     }

//     String getDepartment() {
//         return department;
//     }

//     String getName() {
//         return name;
//     }
// }
