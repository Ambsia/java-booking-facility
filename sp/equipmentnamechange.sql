

Select BookingEquipment from tblBookings
WHERE BookingEquipment != 'iPads' AND BookingEquipment != 'iPads (J)' and BookingEquipment != 'Laptops'

Select BookingEquipment from tblBookings
WHERE BookingEquipment != 'iPads' AND BookingEquipment != 'iPads (J)' AND BookingEquipment != 'Laptops'
AND BookingEquipment != 'Screen, Sound, Projector, Microphones Setup' AND BookingEquipment != 'Video Camera'
AND BookingEquipment != 'Exam Laptop' AND BookingEquipment != 'Headphones' 

Select BookingEquipment from tblBookings
WHERE BookingEquipment like '%iPad%' 
AND BookingEquipment LIKe '%([a-z])%' 
AND BookingEquipment != 'iPads (J)' 
AND BookingEquipment != 'iPads'

select bookingEquipment from tblBookings
where BookingEquipment like '%Laptops%' and BookingEquipment not like '%3 x%'

Select BookingEquipment from tblBookings
WHERE BookingEquipment like '%imovie%' 

Select BookingEquipment from tblBookings
Where BookingEquipment like '%Headphones%'

update tblBookings
set BookingEquipment = 'Laptops'
where BookingEquipment like '%Laptops%' and BookingEquipment not like '%3 x%'



UPDATE tblBookings
	set BookingEquipment = 'Screen, Sound, Projector, Microphones Setup'
Where BookingEquipment like '%Pro%'

UPDATE tblBookings
	set BookingEquipment = 'Screen, Sound, Projector, Microphones Setup'
Where BookingEquipment = 'HP Laptop, sound, network, projector, internet'

UPDATE tblBookings
	set BookingEquipment = 'Screen, Sound, Projector, Microphones Setup'
where BookingEquipment = 'HP Laptop, sound, network, projector.' OR
BookingEquipment = 'Screen, sound, projector setup.' OR
BookingEquipment = 'Screen, Sound, Projector, Microphones Setup' OR
BookingEquipment = 'HP Laptop - Sound and Network' OR
BookingEquipment = 'HP Laptop, sound, network, pro'OR
BookingEquipment = 'Laptop and Projector' OR
BookingEquipment = 'HP Laptop - Sound and Network' OR
BookingEquipment = 'Laptop, Projector, Screen' OR
BookingEquipment = 'Projector Laptop, sound, screen' OR
BookingEquipment = 'HP Laptop - Sound and Network' OR
BookingEquipment = 'Projector'OR
BookingEquipment = 'Laptop, projector, sound, screen' OR
BookingEquipment = 'Laptop, sound, screen' OR
BookingEquipment = 'Laptop and Projector Screen'

UPDATE tblBookings
	set BookingEquipment = 'Video Camera'
where BookingEquipment  ='Video Camera, plugged in to mains cannot die FILES' or
BookingEquipment = 'Tripod and Video Camera'  or
BookingEquipment = 'Video Camera and Tripod'  or
BookingEquipment = 'Video Camera and Tripod'  or
BookingEquipment = 'Video Camera and Tripod'

UPDATE tblBookings
	set BookingEquipment = 'Headphones'
Where BookingEquipment like '%Headphones%'

UPDATE tblBookings
	set BookingEquipment = 'Laptops'
Where BookingEquipment like '%Laptop Trolley%'


UPDATE tblBookings
	set BookingEquipment = 'Laptops'
Where BookingEquipment like '14 + 2 Spare (16 in total)'

UPDATE tblBookings
	set BookingEquipment = 'Exam Laptop'
Where BookingEquipment like '%Given Darren Laptop%'

UPDATE tblBookings
	set BookingEquipment = 'Exam Laptop'
Where BookingEquipment like '%Exam Laptop (2)%'


UPDATE tblBookings
	set BookingEquipment = 'Exam Laptop'
Where BookingEquipment like '%Exam Laptop x3 and 3 memory stucks%'


Delete from tblBookings where BookingEquipment = 'Interview Support - JAY' OR
BookingEquipment = '3 Computers from A4 to 5, change printer,' or  
BookingEquipment = 'YO JAY - BRING A LAPTOP TO LESLEY' or
BookingEquipment = 'Sound, live  training event' or
BookingEquipment = 'Vdieo Camera (Unreturned)' or
BookingEquipment = 'Same as above, use the same kit?' or
BookingEquipment = 'Someone there to check up' or
BookingEquipment = 'Big Screen, Sound, has own laptop with video' or 
BookingEquipment = 'Presentation' or 
BookingEquipment = 'Meeting - Alex GO GOG O' or
BookingEquipment = 'Collect Laptop and Charger' or
BookingEquipment = 'Big Screen, Sound, has own laptop with video' or 
BookingEquipment = 'Presentation'

UPDATE tblBookings
	set BookingEquipment = 'Scoreboard'
Where BookingEquipment like '%Scoreboard movie, speakers. Find Naomi.%'
  

UPDATE tblBookings
set BookingEquipment = 'Screen, Sound, Projector, Microphones Setup'
WHERE BookingEquipment like 'Laptop and Projector Screen' or
BookingEquipment like 'Laptop/screen/sound' or
BookingEquipment like 'Laptop to TV and Sound' or
BookingEquipment like 'Screen, Sound, Projector, Microphones Setup' or
BookingEquipment like 'Tv, sound, microphones' or
BookingEquipment like 'HP Laptop, sound, network, projector, internet ' or
BookingEquipment like 'Screen, sound, projector setup.' or
BookingEquipment like 'HP Laptop, Sound, internet and microphones' or
BookingEquipment like 'Video camere and extension lead (power lead) tripo' or
BookingEquipment like 'HP Laptop, sound, network, pro (clear up at 12:55' or
BookingEquipment like 'sound, projector, laptop' or
BookingEquipment like 'HP Laptop, sound,network' or
BookingEquipment like 'Mac/Projector/Smart' or
BookingEquipment like 'Big screen, Laptop, Sound, no internet' or
BookingEquipment like 'Laptop, sound, mics, big projector?' or
BookingEquipment like 'Projector and a Screen and Speakers' or
BookingEquipment like 'Laptop, projector, sound - hand held mic' or
BookingEquipment like 'HP Laptop, sound, network, pro (clear up at 12:55' or
BookingEquipment like 'Projector/Laptop/Sound' or
BookingEquipment like 'Channel 4 Live, Projector, Laptop, Internet, Sound' or
BookingEquipment like 'Projector, Screen, Sound, Laptop' or
BookingEquipment like 'Mics, Mac, projector, sound' or
BookingEquipment like 'Screen, Projector, Laptop' or
BookingEquipment like 'laptop, sound, projection, camera' or
BookingEquipment like 'Laptop, Big Pro, Sound' or
BookingEquipment like 'Screen, Projector, Laptop' or
BookingEquipment like 'Laptop with projector (With Sound)' or
BookingEquipment like ' HP Laptop, sound, network, pro (clear up at 12:55' or
BookingEquipment like 'Screen, Projector, Laptop' or
BookingEquipment like 'Projector, Screen, Network, Sound, Laptop' or
BookingEquipment like 'Projector and a Screen and Laptop' or
BookingEquipment like 'Screen, Projector, Laptop' or
BookingEquipment like 'big screen, laptop, sound, microphones' or
BookingEquipment like 'Computer, screen, sound, network' or
BookingEquipment like 'big screen, laptop, sound, microphones' or
BookingEquipment like 'Stand alone screen, laptop, sound' or
BookingEquipment like 'Laptop, Big Screen, Sound' or
BookingEquipment like 'big screen, laptop, sound, microphones' 


UPDATE tblBookings
set BookingEquipment = 'Laptops'
WHERE BookingEquipment = 'Laptops (about 4 or 5)' or
BookingEquipment = 'Laptops (12)' or BookingEquipment = 'Laptops (about 4 or 5)' or
BookingEquipment = 'Laptops (12)' or BookingEquipment = 'Laptops (about 4 or 5)' or
BookingEquipment = 'Laptops (12)'


UPDATE tblBookings
set BookingEquipment = 'Laptops'
WHere BookingEquipment like '%([0-9]) Laptops%' or
BookingEquipment like '%[0-9] Laptops%' or
BookingEquipment like '%Laptops [0-9]%' or
BookingEquipment like '%Laptops ([0-9])%'


UPDATE tblBookings
set BookingEquipment = 'Laptops'
WHERE BookingEquipment like '9 Laptops (2 are spare) Spanish' or
BookingEquipment like '8 Laptops + Heads (O:\Teaching\For Pupils\Spanish)' or
BookingEquipment like 'Laptops (7)' or
BookingEquipment like 'Laptop' 

UPDATE tblBookings
set BookingEquipment = 'Video Camera'
WHERE BookingEquipment like '%Video Camera%'

Delete from tblBookings where BookingEquipment = 'Just go and see whats going down'
 or BookingEquipment = 'Help setting up USB presentation to the board.'
 or BookingEquipment = 'YO JAY - BRING A LAPTOP TO LESLEY'
 or BookingEquipment = ''



UPDATE tblBookings
set BookingEquipment = 'Screen, Sound, Projector, Microphones Setup'
WHERE BookingEquipment like 'Hall - Sound/Vision/Laptop/Mic'

UPDATE tblBookings
set BookingEquipment = 'Screen, Sound, Projector, Microphones Setup'
WHERE BookingEquipment like '%Laptop, Screen%'

UPDATE tblBookings
set BookingEquipment = 'Screen, Sound, Projector, Microphones Setup'
WHERE BookingEquipment like 'Laptop, sound, screen mics?'

UPDATE tblBookings
set BookingEquipment = 'Screen, Sound, Projector, Microphones Setup'
WHERE BookingEquipment like 'Computer, Screen, internet, Sound, (they have to s'

UPDATE tblBookings
	set BookingEquipment = 'iPads'
WHERE BookingEquipment like '%iPad%' AND BookingEquipment != 'iPads' AND BookingEquipment != 'iPads (J)'



UPDATE tblBookings
	set BookingEquipment = 'Exam Laptop'
WHERE BookingEquipment = '1 Exam Laptop'

UPDATE tblBookings
	set BookingEquipment = 'iPads'
WHERE BookingEquipment = 'iPads 10'

UPDATE tblBookings
	set BookingEquipment = 'Laptops'
WHERE BookingEquipment = 'Laptop'

UPDATE tblBookings
	set BookingEquipment = 'Screen, Sound, Projector, Microphones Setup'
WHERE BookingEquipment = 'infront of tiered seats, laptop, sound, big screen'

UPDATE tblBookings
	set BookingEquipment = 'Video Camera'
WHERE BookingEquipment = 'Video Camera - With Tripod'

UPDATE tblBookings
	set BookingEquipment = 'Video Camera'
WHERE BookingEquipment = 'Camera and Tripod'

UPDATE tblBookings
	set BookingEquipment = 'Video Camera'
WHERE BookingEquipment = 'School Camera - Take photos of World Book Day for'


UPDATE tblBookings
	set BookingEquipment = 'Tripod'
WHERE BookingEquipment = 'camera tripod'



UPDATE tblBookings
	set BookingEquipment = 'Dance Studio Laptop'
WHERE BookingEquipment = 'Laptop with "Dance Studio" label on, Sound, Intern'

UPDATE tblBookings
	set BookingEquipment = 'Exam Laptop'
WHERE BookingEquipment like '%2 Laptop - One with internet%'

UPDATE tblBookings
	set BookingEquipment = 'iPads' 
WHERE BookingEquipment like '%imovie%' 

UPDATE tblBookings
	set BookingEquipment = 'iPads' 
WHERE BookingEquipment like '%iPad%' 
AND BookingEquipment LIKe '%([a-z])%' 
AND BookingEquipment != 'iPads (J)' 
AND BookingEquipment != 'iPads'


UPDATE tblBookings
	set BookingEquipment = 'iPads' 
WHERE BookingEquipment like '%iPad%' AND BookingEquipment LIKe '%any%'


UPDATE tblBookings
	set BookingEquipment = 'iPads' 
WHERE BookingEquipment like '%iPad%' AND BookingEquipment LIKe '%any%'

UPDATE tblBookings
	set BookingEquipment = 'iPads (J)' 
WHERE BookingEquipment = 'iPad (J)'


UPDATE tblBookings
	set BookingEquipment = 'iPads'
WHERE BookingEquipment = 'iPad B'




UPDATE tblBookings
set BookingEquipment = 'Laptop'
where BookingEquipment like '%[0-9]% Laptop' 


UPDATE tblBookings
	set BookingEquipment = 'Laptops'
WHERE BookingEquipment LIKE '%[0-9]% Laptops'

SELECT BookingEquipment FROM tblBookings WHERE BookingEquipment 
LIKE '%[0-9]% Laptop' 
AND BookingEquipment NOT LIKE '%Project%'