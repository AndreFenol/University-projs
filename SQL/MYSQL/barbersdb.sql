CREATE TABLE Barbers (
    BarberID INT PRIMARY KEY,
    BarberName VARCHAR(50) NOT NULL
);

CREATE TABLE Services (
    ServiceID INT PRIMARY KEY,
    ServiceName VARCHAR(50) NOT NULL
);

CREATE TABLE Appointments (
    AppointmentID INT PRIMARY KEY,
    CustomerName VARCHAR(50) NOT NULL,
    ServiceID INT,
    BarberID INT,
    AppointmentDate DATE NOT NULL,
    FOREIGN KEY (ServiceID) REFERENCES Services(ServiceID),
    FOREIGN KEY (BarberID) REFERENCES Barbers(BarberID)
);

-- Insert initial data
INSERT INTO Barbers (BarberID, BarberName) VALUES
(1, 'Any Staff'),
(2, 'Xylgei'),
(3, 'Clark');
(4, 'Joshua');

INSERT INTO Services (ServiceID, ServiceName) VALUES
(1, 'Haircut'),
(2, 'Beard Trim'),
(3, 'Haircut + Beard Trim'),
(4, 'Shave'),
(5, 'Haircut + Shave'),
(6, 'Children\'s Haircut (12 under)');