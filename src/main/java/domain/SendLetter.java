package domain;

import java.text.MessageFormat;

public class SendLetter {
    private final String name;
    private final Mailbox from;
    private String to;
    private String subject;
    private String text;

    public SendLetter(String name, Mailbox mailbox) {
        this.name = name;
        this.from = mailbox;
    }

    public void setTo(String to) { this.to = to; }
    public String getTo() { return to; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getSubject() { return subject; }

    public void setText(String text) { this.text = text; }
    public String getText() { return text; }
    public Mailbox getFrom() { return from; }

    //public void setName(String name) { this.name = name;}
    public String getName() { return name; }

    public String toString() {
        if (subject == null) {
            return MessageFormat.format(
                    """
                            Отправитель: {0}
                            Кому: {1}
                            
                            {2}""",
                    from.getEmail(), to, text
            );
        }
        return MessageFormat.format(
                """
                        Отправитель: {0}
                        Кому: {1}
                        Тема: {2}
                        
                        {3}""",
                from.getEmail(), to, subject, text
        );
    }
}
