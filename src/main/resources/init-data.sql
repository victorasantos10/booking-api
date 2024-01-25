INSERT INTO Property (id, name, address, createdDateTime, updatedDateTime) VALUES('91b23fb9-d079-40aa-84e5-4c438ce99411', 'Home in Beverly Hills', '9439 Santa Monica Blvd, Beverly Hills, CA 90210', CURRENT_TIMESTAMP(), null);
INSERT INTO Guest (id, name, dateOfBirth, email, phone, createdDateTime, updatedDateTime) VALUES ('178147cd-b3e6-4e64-91e2-af16bd22c8f0', 'Victor Alencar', '1995-05-03', 'victor@testapplication.com', '+5511912345678', CURRENT_TIMESTAMP(), null);
INSERT INTO PropertyTeamMember (id, propertyId, name, type) VALUES
('4d9fe11e-f11c-4f22-8bcf-9bfdf6cc2255', '91b23fb9-d079-40aa-84e5-4c438ce99411', 'John Doe', 1),
('76ff6fa9-5423-43a2-90e9-6f441b583f70', '91b23fb9-d079-40aa-84e5-4c438ce99411', 'Peter Parker', 2);
