INSERT INTO `users` (`user_id`, `description`, `email`, `password`, `role`, `username`, `image`, `phone_number`)
VALUES
(1, 'Software engineer with over 5 years of experience.', 'john.smith@example.com', '1234912349', 0, 'john_smith', NULL, NULL),
(2, 'Avid traveler who loves to explore new places.', 'jane.doe@example.com', '1234912349', 0, 'jane_doe', NULL, 21),
(3, 'Web developer with over 3 years of experience.', 'konstantin@example.com', '$2a$10$xsfOG/ivAplxieEid0Pz1emD5nMKi0T4zTVwzeIouHhx3dczVDTRS', 0, 'konstantin', NULL, '23498778'),
(4, 'College student majoring in computer science.', 'user1@example.com', '$2a$10$BExhCJs6P0DSoovW4xHOIOhvYb3dXMocitYBRXTc87vwi3D5v3/XO', 0, 'user1', NULL, '21324453'),
(5, 'Freelance graphic designer with over 7 years of experience.', 'ralfs@example.com', '$2a$10$3XifckhSn80bqzLX9V25p.q2d7VzGDB5xxPJlfJ6SQrAYIrURk0Mi', 0, 'ralfs', NULL, '20202020'),
(6, 'Professional photographer based in New York City.', 'elizabete@example.com', '$2a$10$sQAPPSR5dREKkCyUxNLhZu/QLr1TGDRKsKfo6nBgtKIMmQ.wZztLq', 0, 'elizabete', NULL, '12345678'),
(7, 'New user interested in buying and selling various items.', 'testy@gmail.com', '$2a$10$a7TsTqXy0pLHBhcq7djNq.FPvlRBqaO11Qw0K9O5/djccXph0kHpm', 0, 'testytheuser', NULL, '22220099'),
(8, 'Experienced user buying and selling on our platform.', 'Konstantin10@barters.web', '$2a$10$FRTxjqrkjx7g5W4VZsYjkeqHkkAwn5rHMuLAfkzsg8ZrTkPAGumiq', 1, 'Konstantin10', NULL, '22322956');
COMMIT;
INSERT INTO `items` (`id`, `category`, `description`, `image`, `state`, `status`, `title`, `user_id`, `date`)
VALUES
(1, 6, 'Beautiful vase, but my cat keeps knocking it over. Needs a new home ASAP.', '/images/20230416042310818vase.jpg', 'new', 'AVAILABLE', 'Vase', 1, '2023-04-16 04:23:10.819147'),
(2, 21, 'Piano for sale, great for practicing your scales or as a decorative piece. Just needs a little love.', '/images/20230416043155820piano.jpg', 'Old', 'AVAILABLE', 'Piano', 1, '2023-04-16 04:31:55.822147'),
(3, 2, 'Fancy leather shoes, perfect for a night out or as a collectors item.', '/images/20230416043243995shoes.jpg', 'old', 'AVAILABLE', 'Shoes', 1, '2023-04-16 04:32:43.996298'),
(4, 2, 'Cuuuuuute knitwear, but my grandma keeps knitting me more. Cannot keep them all!', '/images/20230416043318312sweater.jpg', 'new', 'AVAILABLE', 'Knitwear', 1, '2023-04-16 04:33:18.312938'),
(5, 14, 'Really old phone, might be a cool vintage piece or a fun project for someone. Bartering it because it does not fit in with my decor. ', '/images/20230416044802610phone_old.jpg', 'old', 'AVAILABLE', 'Really old Phone', 1, '2023-04-16 04:48:02.611774'),
(6, 6, 'Vintage lamp, works perfectly fine but I upgraded to something newer.', '/images/20230416155331409lamp.jpg', 'good enough. No issues.', 'AVAILABLE', 'Vintage lamp.', 1, '2023-04-16 15:53:31.410343'),
(7, 21, 'Wooden desk, has been well-loved but still has plenty of life left. ', '/images/20230416155506019desk.jpg', 'Used', 'AVAILABLE', 'Wooden Desk', 1, '2023-04-16 15:55:06.020479'),
(8, 13, 'Robot toy, still in its packaging and waiting for someone to play with it. ', '/images/20230416155601660robot.jpg', 'NEW', 'AVAILABLE', 'Robot toy.', 1, '2023-04-16 15:56:01.662817'),
(9, 14, 'Items organizer, helps keep everything in its place but I have outgrown it.', '/images/20230416155658035organizer.jpg', 'new', 'AVAILABLE', 'Items Organizer', 1, '2023-04-16 15:56:58.036834'),
(10, 7, 'Real gold necklace, selling because I have too much jewelry and need to downsize.', '/images/20230416155945532jew.jpg', 'used, but gold never gets old.', 'AVAILABLE', 'Necklace', 1, '2023-04-16 15:59:45.533327');
COMMIT;

INSERT INTO `trades` (`comment`, `date`, `status`, `item_id`, `offered_item_id`)
VALUES
( 'Hello there! I am quite interested in your item. Deal?', '2023-04-16 04:53:55.597867', 'PENDING', 1, 2),
('Why hello there! Your item has caught my eye. How about we strike a bargain?', '2023-04-16 04:54:01.897405', 'PENDING', 3, 4),
('Greetings! Your item looks very intriguing. I am interested in making a trade.', '2023-04-16 10:33:10.428915', 'PENDING', 2, 4);
COMMIT;

INSERT INTO `reviews` (`review_id`, `comment`, `grade`, `reviewed_id`, `reviewer_id`)
VALUES
(1, 'The seller claimed my cat was included in the package but it was not. It was a misunderstanding.', 4, 2, 1),
(2, 'I had a negative experience with this buyer.', 4, 1, 2),
(3, 'Great seller! The item arrived quickly and was in excellent condition.', 1, 2, 1),
(4, 'Excellent seller! The item arrived quickly and was in even better condition than described.', 2, 2, 1),
(5, 'Smooth transaction with this seller. The item was as described and arrived on time.', 3, 2, 1),
(6, 'Very friendly seller and easy to deal with. Thank you!', 2, 2, 1),
(7, 'I had a great experience buying from this seller.', 3, 2, 1);
COMMIT;