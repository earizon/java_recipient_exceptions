package org.oficina24x7.qa;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * @author Enrique Arizón Benito
 *
 */
public class RecipientException extends java.lang.RuntimeException {

    private static final long serialVersionUID = 1L;

    public enum RecipientType {
        USER(0),          // client that originates the request, final user, robot, ...
        ADMINISTRATOR(1), // dev.ops, system admin, deployment tool in charge of fixing a network/disk/resource/... failure
        DEVELOPER(2);     // software developer in charge of fixing an assert condition.

        private final int recipientCode;

        RecipientType(int recipientCode) {
            this.recipientCode = recipientCode;
        }

        public int getRecipientCode() {
            return this.recipientCode;
        }

        public static RecipientType valueOf(int recipientCode) {
            switch (recipientCode) {
                case 0:
                    return RecipientType.USER;
                case 1:
                    return RecipientType.ADMINISTRATOR;
                case 2:
                    return RecipientType.DEVELOPER;
                default:
                    throw new IllegalArgumentException("Invalid recipientCode " + recipientCode + ", version: " + serialVersionUID);
            }
        }
    }

    private static final Throwable NO_SOURCE_EXCEPTION = new Throwable(); // constant to avoid ugly nulls
    private final RecipientType recipientType;
    private final String        description;
    private final String        detail;
    private final String        solution;
    private final Throwable     sourceException;

    private static final String NO_LINKED_INFO = ""; // constant to avoid ugly nulls
    private Object linkedInfo = NO_LINKED_INFO; // Default value

    // PUBLIC Constructors {
    public RecipientException(RecipientType recipient, String description, String detail, String solution, Throwable sourceException) {
        this.recipientType   = recipient;
        this.description     = description;
        this.detail          = detail;
        this.solution        = solution;
        this.sourceException = sourceException;
    }

    public RecipientException(RecipientType recipient, String description, String detail, String solution) {
        this(recipient, description, detail, solution, NO_SOURCE_EXCEPTION);
    }

    public RecipientException(RecipientType recipient, String description, String detail) {
        this(recipient, description, detail, "", NO_SOURCE_EXCEPTION);
    }

    public RecipientException(RecipientType recipient, String description) {
        this(recipient, description, "", "", NO_SOURCE_EXCEPTION);
    }

    // } Public getters {
    public Throwable getSourceException() {
        if (sourceException == NO_SOURCE_EXCEPTION) {
            throw new RecipientException(RecipientType.DEVELOPER, 
             "no source exception attached", "", "Use hasSourceException() first to check it. ", new Throwable() /*sourceException*/);
        }
        return sourceException;
    }

    public RecipientType getRecipientType() {
        return recipientType;
    }

    public String getDescription() {
        return description;
    }

    public String getDetail() {
        return detail;
    }

    public String getSolution() {
        return solution;
    }


    /**
     * allows to attach extra information through a linked object.
     * It's used as an extension point for libraries and frameworks 
     * to add any useful information such as ERROR CODES, timestamps, ....
     * Note: This field is not considered inmutable since any module or software
     *    layer can add/modify the object with information extracted from the context.
     * 
     * Note: ERROR CODE is considered an anti-pattern in this library: 
     *    ERROR CODEs naïvely try to define a fixed number of errors in the API.
     * This just doesn't work but for the most simple software with simple
     * internal states. Most software will have an undefined (and growing)
     * number of potential exceptions and recipients in charge of handling the
     * exception will just limit to to search into a table the meaning of such error code.
     * RecipientException tries to avoid this by forcing a human-readable
     * String description.
     * (Exception to this rule could be an application highly integrated with some
     * deployment tool, but even in such case the description can be used
     * to transmit the ERRCODE).
     * 
     * @param linkedInfo
     */
    public void setLinkedInfo(Object linkedInfo) {
        this.linkedInfo = linkedInfo;
    }

    /**
     * Returns reference to linked object with extra information
     * 
     * @return linkedInfo object reference
     */
    public Object getLinkedInfo() {
        if (linkedInfo == NO_LINKED_INFO) {
            throw new RecipientException(RecipientType.DEVELOPER, 
             "no linkedInfo attached", "", "Use hasLinkedInfor() first to check it. ", new Throwable() /*sourceException*/);
        }
        return linkedInfo;
    }
    
    // } public has* {

    public boolean hasSourceException() {
        return sourceException != NO_SOURCE_EXCEPTION;
    }

    public boolean hasLinkedInfo() {
        return linkedInfo != NO_LINKED_INFO;
    }



    // } toString {
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("recipient type:{").append(recipientType).append("}")
          .append(", description: {").append(description  ).append("}")
          .append(", detail:      {").append(detail       ).append("}")
          .append(", solution:    {").append(solution     ).append("}");

        if (sourceException != NO_SOURCE_EXCEPTION) {
            // Dump stack-trace
            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter( writer );
            sourceException.printStackTrace( printWriter );
            printWriter.flush();
            sb.append(writer.getBuffer());
        }

        return sb.toString();
    }
    // } equals {
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof RecipientException)) { return false; }
        if(obj == this) { return true; }
        RecipientException other = (RecipientException) obj;
        return  recipientType  .equals(other.getRecipientType())
             && description    .equals(other.description       )
             && detail         .equals(other.detail            )
             && solution       .equals(other.solution          )
             && sourceException.equals(other.sourceException   )
             && linkedInfo     .equals(other.linkedInfo        )
        ;
    }
}
