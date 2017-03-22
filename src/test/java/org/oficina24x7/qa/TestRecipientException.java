package org.oficina24x7.qa;

import static org.junit.Assert.*;

import org.junit.Test;

import org.oficina24x7.qa.RecipientException.RecipientType;
public class TestRecipientException {

    @Test
    public void testConstructors() {
        RecipientException r1 = new RecipientException(RecipientType.USER, "description", "detail", "solution", new Throwable() /*sourceException*/);
        RecipientException r2 = new RecipientException(RecipientType.USER, "description", "detail", "solution");
        RecipientException r3 = new RecipientException(RecipientType.USER, "description", "detail");
        RecipientException r4 = new RecipientException(RecipientType.USER, "description");
                           r4.setLinkedInfo(new java.util.ArrayList<Integer>());
        assertTrue (r1.hasSourceException());
        assertFalse(r1.hasLinkedInfo());
        assertFalse(r2.hasSourceException());
        assertFalse(r3.hasLinkedInfo() || r3.hasSourceException());
        assertTrue (r4.hasLinkedInfo());
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
    
    @Test
    public void implementationDetails() {
        RecipientException re1 = new RecipientException(RecipientType.USER, "description");
        assertTrue("re1.getSourceException().toString()'"+re1.getSourceException().toString()+
                "' must give hint about default object: ", re1.getSourceException().toString().length() > 0);

    }
}
