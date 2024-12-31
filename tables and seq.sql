create Sequence event_seq 
start  1
increment 1
cache 10
;

create Sequence participants_seq 
start 1
increment 1
cache 10
;

create Sequence tickets_seq 
start 1
increment 1
cache 10
;

create Sequence feedback_seq 
start 1
increment 1
cache 10
;

create Sequence logistics_seq 
start 1
increment 1
cache 10
;

create Sequence event_expenses_seq 
start 1
increment 1
cache 10
;

create Sequence event_attendees_seq 
start 1
increment 1
cache 10
;

create Sequence promotions_seq 
start 1
increment 1
cache 10
;

create Sequence income_seq 
start 1
increment 1
cache 10
;

create Sequence staff_seq 
start 1
increment 1
cache 10
;
create Sequence CUSTOMER_SEQ 
start 1
increment 1
cache 10
;

create Sequence Organizers_seq 
start 1
increment 1
cache 10
;

create Sequence sponsors_seq 
start 1
increment 1
cache 10
;

CREATE TABLE Organizers (
    Organizer_ID  INT PRIMARY KEY DEFAULT NEXTVAL('Organizers_seq'),
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Phone_Number VARCHAR(15) NOT NULL
);

-- Table for Staff
CREATE TABLE Staff (
    Staff_ID int PRIMARY KEY DEFAULT NEXTVAL('staff_seq') ,
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Phone_Number VARCHAR(15) NOT NULL,
    Role VARCHAR(50) NOT NULL
);

-- Table for Customers
CREATE TABLE Customers (
    Customer_ID int PRIMARY KEY default nextval('CUSTOMER_SEQ'),
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Phone_Number VARCHAR(15)UNIQUE NOT NULL
);

-- Table for Events
CREATE TABLE Events (
    Event_ID int PRIMARY KEY default nextval('event_seq'),
    Name VARCHAR(255) NOT NULL,
    Date DATE NOT NULL,
    Time TIMESTAMP NOT NULL,
    Type_Of_Event VARCHAR(50) NOT NULL,
    Organizer_ID INT NOT NULL,
    Customer_ID INT NOT NULL,
    Payment DECIMAL(10, 2) NOT NULL,
    Status VARCHAR(50),
    FOREIGN KEY (Organizer_ID) REFERENCES Organizers(Organizer_ID),
    FOREIGN KEY (Customer_ID) REFERENCES Customers(Customer_ID)
);

-- Table for Tickets
CREATE TABLE Tickets (
    Ticket_ID int PRIMARY KEY default nextval('tickets_seq'),
    Event_ID INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Type_Of_Ticket VARCHAR(10) CHECK (Type_Of_Ticket IN ('VIP', 'Regular')),
    Services VARCHAR(255) NOT NULL,
    Status VARCHAR(50) NOT NULL,
    FOREIGN KEY (Event_ID) REFERENCES Events(Event_ID)
);
--int PRIMARY KEY default nextval('participants_seq'),
--Table for Sponsers
CREATE TABLE Sponsors (
    Sponsor_ID INT DEFAULT NEXTVAL('sponsors_seq') PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(150) NOT NULL,
    Event_ID INT NOT NULL,
    FOREIGN KEY (Event_ID) REFERENCES Events(Event_ID)
);

-- Table for Attendees
CREATE TABLE participants (
    participant_ID int PRIMARY KEY default nextval('participants_seq'),
    Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE,
    Phone_Number VARCHAR(15),
    Ticket_ID INT NOT NULL,
   
    FOREIGN KEY (Ticket_ID) REFERENCES Tickets(Ticket_ID)
);
--Table for 


CREATE TABLE Event_Attendees (
    Attendee_ID INT PRIMARY KEY DEFAULT NEXTVAL('event_attendees_seq'),
    Participant_ID INT NOT NULL,
    check_in_time TIMESTAMP NOT NULL,
    FOREIGN KEY (Participant_ID) REFERENCES Participants(Participant_ID)
);

-- Table for Feedback
CREATE TABLE Feedback (
    Feedback_ID  int PRIMARY KEY default nextval('feedback_seq'),
    Event_ID INT NOT NULL,
    Attendee_ID INT NOT NULL,
    Comment TEXT,
    Rating INT CHECK (Rating BETWEEN 1 AND 5),
    FOREIGN KEY (Event_ID) REFERENCES Events(Event_ID),
    FOREIGN KEY (Attendee_ID) REFERENCES Event_Attendees(Attendee_ID)
);

-- Table for Promotions
CREATE TABLE Promotions (
    Promotion_ID int PRIMARY KEY default nextval('promotions_seq'),
    Event_ID INT NOT NULL,
    Cost DECIMAL(10, 2) NOT NULL,
    Description TEXT,
    FOREIGN KEY (Event_ID) REFERENCES Events(Event_ID)
);

-- Table for Logistics
CREATE TABLE Logistics (
    Logistic_ID int PRIMARY KEY default  nextval('logistics_seq'),
    Item_name VARCHAR(255) NOT NULL,
    Quantity INT NOT NULL CHECK (Quantity >= 0),
    Cost_Per_Unit DECIMAL(10, 2) NOT NULL CHECK (Cost_Per_Unit >= 0)
);

-- Table for Event Logistics
CREATE TABLE Event_Logistics (
    Logistic_ID INT NOT NULL,
    Event_ID INT NOT NULL,
    Quantity INT NOT NULL CHECK (Quantity >= 0),
    FOREIGN KEY (Logistic_ID) REFERENCES Logistics(Logistic_ID),
    FOREIGN KEY (Event_ID) REFERENCES Events(Event_ID)
);

-- Table for Budget
CREATE TABLE Income (
    Income_ID int PRIMARY KEY default nextval('income_seq'),
    Event_ID INT NOT NULL,
    Source VARCHAR(255) NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL CHECK (Amount >= 0),
    Date_Received DATE NOT NULL,
    Description TEXT ,
    FOREIGN KEY (Event_ID) REFERENCES Events(Event_ID)
);

-- Table for Income
CREATE TABLE Income (
    Income_ID SERIAL PRIMARY KEY,
    Event_ID INT NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (Event_ID) REFERENCES Events(Event_ID)
);

-- Table for Event Expenses
CREATE TABLE Event_Expenses (
    Event_Expenses_ID int PRIMARY KEY default nextval('event_expenses_seq'),
    Event_ID INT NOT NULL,
    Source VARCHAR(255) NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL CHECK (Amount >= 0),
    Date_Received DATE NOT NULL,
    Description TEXT ,
    FOREIGN KEY (Event_ID) REFERENCES Events(Event_ID)
);

INSERT INTO Organizers (Name, email,phone_number) VALUES ('Salah Mohamed', 'Salah@gmail.com','+987654321');
alter table events 
alter column time type TIME 
select *
from events

select *
from income

CREATE OR REPLACE FUNCTION update_income_on_event_insert()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO income ( event_id, amount, date_received, Source)
    VALUES ( NEW.Event_ID, NEW.Payment, NEW.date, 'Payment from event');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE or replace TRIGGER insert_income_trigger
AFTER INSERT ON events
FOR EACH ROW
EXECUTE FUNCTION update_income_on_event_insert();
