package com.example.bfhl;

import com.example.bfhl.dto.BfhlRequest;
import com.example.bfhl.dto.BfhlResponse;
import com.example.bfhl.service.BfhlService;
import com.example.bfhl.service.BfhlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BfhlServiceTest {

    private BfhlService service;

    @BeforeEach
    void setUp() {
        service = new BfhlServiceImpl();
    }

    @Test
    void testExampleA() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "1", "334", "4", "R", "$"));
        BfhlResponse response = service.process(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of("1"), response.getOddNumbers());
        assertEquals(List.of("334", "4"), response.getEvenNumbers());
        assertEquals(List.of("A", "R"), response.getAlphabets());
        assertEquals(List.of("$"), response.getSpecialCharacters());
        assertEquals("339", response.getSum());
        assertEquals("Ra", response.getConcatString());
    }

    @Test
    void testExampleB() {
        BfhlRequest request = new BfhlRequest(
                Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        BfhlResponse response = service.process(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of("5"), response.getOddNumbers());
        assertEquals(List.of("2", "4", "92"), response.getEvenNumbers());
        assertEquals(List.of("A", "Y", "B"), response.getAlphabets());
        assertEquals(List.of("&", "-", "*"), response.getSpecialCharacters());
        assertEquals("103", response.getSum());
        assertEquals("ByA", response.getConcatString());
    }

    @Test
    void testExampleC() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "ABCD", "DOE"));
        BfhlResponse response = service.process(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of(), response.getOddNumbers());
        assertEquals(List.of(), response.getEvenNumbers());
        assertEquals(List.of("A", "ABCD", "DOE"), response.getAlphabets());
        assertEquals(List.of(), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("EoDdCbAa", response.getConcatString());
    }

    @Test
    void testEmptyArray() {
        BfhlRequest request = new BfhlRequest(List.of());
        BfhlResponse response = service.process(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of(), response.getOddNumbers());
        assertEquals(List.of(), response.getEvenNumbers());
        assertEquals(List.of(), response.getAlphabets());
        assertEquals(List.of(), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    void testNegativeNumbers() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("-1", "-4", "3"));
        BfhlResponse response = service.process(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of("-1", "3"), response.getOddNumbers());
        assertEquals(List.of("-4"), response.getEvenNumbers());
        assertEquals("-2", response.getSum());
    }

    @Test
    void testMixedAlphanumericTreatedAsSpecialCharacter() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a1", "12b"));
        BfhlResponse response = service.process(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of("a1", "12b"), response.getSpecialCharacters());
        assertEquals(List.of(), response.getAlphabets());
        assertEquals(List.of(), response.getOddNumbers());
        assertEquals(List.of(), response.getEvenNumbers());
    }

    @Test
    void testUserIdAndIdentityFields() {
        BfhlRequest request = new BfhlRequest(List.of());
        BfhlResponse response = service.process(request);

        assertEquals("animesh_soni_27102004", response.getUserId());
        assertEquals("animeshsoni777@gmail.com", response.getEmail());
        assertEquals("2310990011", response.getRollNumber());
    }
}
