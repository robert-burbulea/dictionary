There is a static collection which has all the necessary info.

I chose a static data structure because in many functions where tha languages
are given as parameter, you have to select the appropriate dictionary.
For this problem, I chose a hashmap of Dictionaries, where the key is the
language.

Also, a lot of the classes are static because, for example, you can't add
a word without having all the dictionaries at hand. The Word class can't
store the entire list of dictionaries and a Word object can't add words
by itself

The Checker class is easy to use