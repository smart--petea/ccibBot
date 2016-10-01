package p.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;

@Entity
public class Company {
    @Id
    @GeneratedValue
    private int id;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Column(length = 100)
    private String href;
    public String getHref() { return href; }
    public void setHref(String href) { this.href = href; }

    @Column(length = 500)
    private String title;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @Column(length = 100)
    private String phones;
    public String getPhones() { return phones; }
    public void setPhones(String phones) { this.phones = phones; }

    @Column(length = 500)
    private String mails;
    public String getMails() { return mails; }
    public void setMails(String mails) { this.mails = mails; }
}

