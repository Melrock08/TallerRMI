-- seed.sql
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS books (
  isbn TEXT PRIMARY KEY,
  title TEXT NOT NULL,
  author TEXT,
  total_copies INTEGER NOT NULL,
  borrowed_copies INTEGER NOT NULL DEFAULT 0
);

DELETE FROM books;

INSERT INTO books (isbn, title, author, total_copies, borrowed_copies) VALUES
('9781234567890','Algoritmos 101','Autor A',3,0),
('9780987654321','Sistemas Distribuidos','Autor B',2,1),
('9781111111111','Bases de Datos','Autor C',1,0),
('9782222222222','Programación en Java','Autor D',4,2),
('9783333333333','Redes de Computadoras','Autor E',5,1),
('9784444444444','Inteligencia Artificial','Autor F',2,0),
('9785555555555','Estructuras de Datos','Autor G',3,1),
('9786666666666','Matemáticas Discretas','Autor H',2,0),
('9787777777777','Ingeniería de Software','Autor I',4,2),
('9788888888888','Cálculo I','Autor J',3,0),
('9789999999999','Física para Ingenieros','Autor K',2,1),
('9781478523690','Teoría de la Computación','Autor L',1,0),
('9781597534862','Compiladores','Autor M',2,0),
('9781682736451','Sistemas Operativos','Autor N',3,1),
('9781789456123','Criptografía','Autor O',2,0),
('9781876543210','Desarrollo Web','Autor P',4,1),
('9781987654321','Machine Learning','Autor Q',2,0),
('9782098765432','Big Data','Autor R',3,1);
