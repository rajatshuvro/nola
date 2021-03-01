package com.nola.NativeSearch.tests;


import com.nola.NativeSearch.Trie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrieTests {
    @Test
    public void AddAndFind(){
        var trie = new Trie();
        trie.Add("Hello");
        trie.Add("world");

        Assertions.assertTrue(trie.Find("hello"));
    }
}
