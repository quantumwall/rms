DELETE FROM document;
DELETE FROM route;
DELETE FROM driver;
DELETE FROM manager;
DELETE FROM cargo;

INSERT INTO cargo (goods, weight)
VALUES  ('Мясо', 10000),
        ('Палеты', 5000),
        ('Котлеты',8000),
        ('Пиво', 15000);

INSERT INTO manager (name)
VALUES  ('Петров Петр Петрович'),
        ('Иванов Иван Иванович');

INSERT INTO driver (name)
VALUES  ('Сидоров Сидор Сидорович'),
        ('Алексеев Алексей Алексеевич');

INSERT INTO route (shipment_date, departure_city, destination_city, bill_number, price, cargo_id, manager_id, driver_id, is_paid)
VALUES  ('2023-04-15', 'Торбеево', 'Саранск', 1234, 15000, 1, 1, 1, true),
        ('2023-04-01', 'Волгоград', 'Самара', 2345, 34000, 4, 1, 2, false),
        ('2023-04-10', 'Атяшево', 'Торбеево', 3245, 23000, 3, 2, 1, false),
        ('2023-03-30', 'Самара', 'Уфа', 8765, 20000, 2, 2, 2, true);

