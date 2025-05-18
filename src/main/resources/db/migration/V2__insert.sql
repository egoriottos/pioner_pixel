INSERT INTO users (name, date_of_birth, password) VALUES
                                                          ( 'User1', '2001-01-01', 'password1'), -- password1
                                                          ( 'User2', '2002-01-01', 'password2'), -- password2
                                                          ( 'User3', '2003-01-01', 'password3'), -- password3
                                                          ( 'User4', '2004-01-01', 'password4'), -- password4
                                                          ( 'User5', '2005-01-01', 'password5'), -- password5
                                                          ( 'User6', '2006-01-01', 'password6'), -- password6
                                                          ( 'User7', '2007-01-01', 'password7'), -- password7
                                                          ( 'User8', '2008-01-01', 'password8'), -- password8
                                                          ( 'User9', '2009-01-01', 'password9'), -- password9
                                                          ( 'User10', '2010-01-01', 'password10'); -- password10

INSERT INTO email_data (email, user_id) VALUES
                                            ('user1_1@example.com', 1),
                                            ('user1_2@example.com', 1),
                                            ('user2_1@example.com', 2),
                                            ('user2_2@example.com', 2),
                                            ('user3_1@example.com', 3),
                                            ('user3_2@example.com', 3),
                                            ('user4_1@example.com', 4),
                                            ('user4_2@example.com', 4),
                                            ('user5_1@example.com', 5),
                                            ('user5_2@example.com', 5),
                                            ('user6_1@example.com', 6),
                                            ('user6_2@example.com', 6),
                                            ('user7_1@example.com', 7),
                                            ('user7_2@example.com', 7),
                                            ('user8_1@example.com', 8),
                                            ('user8_2@example.com', 8),
                                            ('user9_1@example.com', 9),
                                            ('user9_2@example.com', 9),
                                            ('user10_1@example.com', 10),
                                            ('user10_2@example.com', 10);

INSERT INTO phone_data (phone, user_id) VALUES
                                            ('+70000000001', 1),
                                            ('+70000000002', 1),
                                            ('+70000000003', 2),
                                            ('+70000000004', 2),
                                            ('+70000000005', 3),
                                            ('+70000000006', 3),
                                            ('+70000000007', 4),
                                            ('+70000000008', 4),
                                            ('+70000000009', 5),
                                            ('+70000000010', 5),
                                            ('+70000000011', 6),
                                            ('+70000000012', 6),
                                            ('+70000000013', 7),
                                            ('+70000000014', 7),
                                            ('+70000000015', 8),
                                            ('+70000000016', 8),
                                            ('+70000000017', 9),
                                            ('+70000000018', 9),
                                            ('+70000000019', 10),
                                            ('+70000000020', 10);
INSERT INTO account (user_id, balance)
VALUES
    (1, 100.00),
    (2, 150.00),
    (3, 200.00),
    (4, 250.00),
    (5, 300.00),
    (6, 350.00),
    (7, 400.00),
    (8, 450.00),
    (9, 500.00),
    (10, 550.00);
