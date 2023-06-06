DELETE FROM document;
DELETE FROM route;
DELETE FROM driver;
DELETE FROM manager;
DELETE FROM cargo;
DELETE FROM customer;

INSERT INTO manager (name)
VALUES  ('Петров Петр Петрович'),
        ('Иванов Иван Иванович');

INSERT INTO driver (name)
VALUES  ('Сидоров Сидор Сидорович'),
        ('Алексеев Алексей Алексеевич');

INSERT INTO customer (name)
VALUES  ('Талина'),
        ('Крафтер'),
        ('Монополия');

INSERT INTO route (shipment_date, customer_id, departure_city, destination_city, bill_number, price, manager_id, driver_id, is_paid)
VALUES  ('2023-04-15', 1, 'Торбеево', 'Саранск', 1234, 15000, 1, 1, true),
        ('2023-04-01', 1, 'Волгоград', 'Самара', 2345, 34000, 1, 2, false),
        ('2023-04-10', 2,'Атяшево', 'Торбеево', 3245, 23000, 2, 1, false),
        ('2023-03-30', 3, 'Самара', 'Уфа', 8765, 20000, 2, 2, true);

INSERT INTO cargo (route_id, product, weight)
VALUES  (1, 'Мясо', 10000),
        (2, 'Палеты', 5000),
        (3, 'Котлеты',8000),
        (4, 'Пиво', 15000);
