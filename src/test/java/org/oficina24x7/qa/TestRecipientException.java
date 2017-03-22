package org.oficina24x7.qa;

import static org.junit.Assert.*;

import org.junit.Test;

import org.oficina24x7.qa.RecipientException.RecipientType;
public class TestRecipientException {

    @Test
    public void testConstructors() {
        new RecipientException(RecipientType.USER, "description", "detail", "solution", new Throwable() /*sourceException*/);
        new RecipientException(RecipientType.USER, "description", "detail", "solution");
        new RecipientException(RecipientType.USER, "description", "detail");
        new RecipientException(RecipientType.USER, "description");
        assertTrue(true);
    }

    @Test
    public void testComparation() {
        Throwable sourceEx = new RuntimeException();
        RecipientException re1 = new RecipientException(RecipientType.USER,          "description", "detail", "solution", sourceEx);
        RecipientException re2 = new RecipientException(RecipientType.ADMINISTRATOR, "description", "detail", "solution", sourceEx);
        RecipientException re3 = new RecipientException(RecipientType.USER,          "description", "detail", "solution", sourceEx);
        org.junit.Assert.assertNotEquals(re1, re2);
        org.junit.Assert.assertEquals   (re1, re3);
    }
}
