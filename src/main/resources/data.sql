INSERT INTO ecosystem
	(ecosystem_id, ecosystem_name, altitude, water)
VALUES
	(1,'Lower Sonoran', 'Less than 1000 ft', 'Less than 4 inches per year'),(2,'Upper Sonoran', '1000 - 2000 ft', '4 - 11 inches per year'),(3, 'Chaparral', '2000 - 3000 ft', '11 - 16 inches per year'),(4, 'Grasslands', '3000 - 4500 ft', '8 - 17 inches per year'),(5, 'Great Basin Conifer', '4500 - 6000 ft', '11 - 16 inches per year'),(6, 'Montane Conifer', '6000 - 7500 ft', '20 - 28 inches per year');
	
INSERT INTO plant 
	(plant_id, flower_color, growth_habit, latin_name, notes) 
VALUES
	(1, 'Taraxacum officinale', 'yellow', 'basal leaf rosette, single flower per stalk', 'invasive, prefers disturbed ground'),(2, 'Larrea tridentata', 'yellow', 'shrub', 'very slow growth, some are thousands of years old.'),(3, 'Carnegiea gigantea', 'white', 'tall branched trunk', 'can store hundreds of gallons of water.'),(4, 'Yucca baccata', 'white and purple', 'long spike leaves, no trunk', 'can be a signal of former human habitation nearby'),(5, 'Achillea millefolium', 'white', 'unorganized basal leaves, flower clusters on stalk', 'used in traditional I-Ching practices.'),(6, 'Rhus trilobata', 'yellowish', 'shrub', 'rapid ragrowth after fire');

INSERT INTO benefit
	(benefit_id, benefit_name)
VALUES
	(1, 'food'),(2, 'medicine'),(3, 'fiber'),(4, 'soap'),(5, 'divination');

INSERT INTO common_name
	(name_id, plant_id, name)
VALUES
	(1, 1, 'dandelion'),(2, 1, 'pissabed'),(3, 2, 'cresosote bush'),(4, 2, 'chapparal'),(5, 3, 'saguaro cactus'),(6, 4, 'banana yucca'),(7, 4, 'Spanish bayonet'),(8, 5, 'yarrow'),(9, 5, 'bloodwort'),(10, 6, 'basket bush'),(11, 6, 'skunkbush');

INSERT INTO plant_benefit
	(benefit_id, plant_id)
VALUES
	(1, 1),(2, 1),(2, 2),(1, 3),(1, 4),(3, 4),(4, 4),(2, 5),(5, 5),(1, 6),(3, 6);

INSERT INTO plant_ecosystem
	(ecosystem_id, plant_id)
VALUES
	(1, 1),(2, 1),(3, 1),(4, 1),(5, 1),(6, 1),(1, 2),(2, 2),(3, 2),(1, 3),(2, 4),(3, 4),(5, 4),(5, 5),(6, 5),(4, 6),(5, 6);
	
	
	