package org.oficina24x7.qa;

import java.io.PrintWriter;
import java.io.StringWriter;


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

    public  static final Throwable NOSOURCE = new Throwable("NO SOURCE EXCEPTION ATTACHED");
    private final RecipientType recipientType;
    private final String        description;
    private final String        detail;
    private final String        solution;
    private final Throwable     sourceException;

    // PUBLIC Constructors {
    public RecipientException(RecipientType recipient, String description, String detail, String solution, Throwable sourceException) {
        this.recipientType   = recipient;
        this.description     = description;
        this.detail          = detail;
        this.solution        = solution;
        this.sourceException = sourceException;
    }

    public RecipientException(RecipientType recipient, String description, String detail, String solution) {
        this(recipient, description, detail, solution, NOSOURCE);
    }

    public RecipientException(RecipientType recipient, String description, String detail) {
        this(recipient, description, detail, "", NOSOURCE);
    }

    public RecipientException(RecipientType recipient, String description) {
        this(recipient, description, "", "", NOSOURCE);
    }

    // } Public getters {
    public Throwable getSourceException() {
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
    // } toString {
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("recipient type:{").append(recipientType).append("}")
          .append(", description: {").append(description  ).append("}")
          .append(", detail:      {").append(detail       ).append("}")
          .append(", solution:    {").append(solution     ).append("}");

        if (sourceException != NOSOURCE) {
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
        ;
    }
}
