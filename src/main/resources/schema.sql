CREATE TABLE IF NOT EXISTS Property (
    id UUID NOT NULL,
    name VARCHAR(255),
    address VARCHAR(255),
    createdDateTime TIMESTAMP,
    updatedDateTime TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS PropertyTeamMember (
    id UUID NOT NULL,
    propertyId UUID,
    name VARCHAR(255),
    type INTEGER,
    createdDateTime TIMESTAMP,
    updatedDateTime TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (propertyId) REFERENCES Property (id)
);

CREATE TABLE IF NOT EXISTS Guest (
    id UUID NOT NULL,
    name VARCHAR(255),
    dateOfBirth DATE,
    email VARCHAR(255),
    phone VARCHAR(255),
    createdDateTime TIMESTAMP,
    updatedDateTime TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Booking (
    id UUID NOT NULL,
    guestId UUID,
    propertyId UUID,
    status INTEGER,
    startDateTime DATE,
    endDateTime DATE,
    adults INTEGER,
    children INTEGER,
    createdDateTime TIMESTAMP,
    updatedDateTime TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (guestId) REFERENCES Guest (id),
    FOREIGN KEY (propertyId) REFERENCES Property (id)
);

CREATE TABLE IF NOT EXISTS Block (
    id UUID NOT NULL,
    propertyId UUID,
    createdByPropertyTeamMemberId UUID,
    startDateTime TIMESTAMP,
    reason VARCHAR(255),
    isActive BIT,
    endDateTime TIMESTAMP,
    createdDateTime TIMESTAMP,
    updatedDateTime TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (propertyId) REFERENCES Property (id)
);