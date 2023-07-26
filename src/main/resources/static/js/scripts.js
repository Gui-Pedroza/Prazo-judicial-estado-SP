function showDaysToAddInput() {
	var dropdown = document.getElementById("days-to-add-dropdown");
	var selectedValue = dropdown.options[dropdown.selectedIndex].value;
	var outroQuantidade = document.getElementById("outro-quantidade");

	if (selectedValue === "outro") {
		outroQuantidade.style.display = "block";
	} else {
		outroQuantidade.style.display = "none";
	}
}

function loadMunicipios() {
	const cidades = [
		"Aguaí",
		"Águas de Lindóia",
		"Agudos",
		"Altinópolis",
		"Americana",
		"Amparo",
		"Américo Brasiliense",
		"Andradina",
		"Aparecida",
		"Araraquara",
		"Araras",
		"Araçatuba",
		"Artur Nogueira",
		"Arujá",
		"Atibaia",
		"Auriflama",
		"Avaré",
		"Bananal",
		"Bariri",
		"Barra Bonita",
		"Barretos",
		"Barueri",
		"Batatais",
		"Bauru",
		"Bebedouro",
		"Bertioga",
		"Bilac",
		"Birigui",
		"Borborema",
		"Botucatu",
		"Bragança Paulista",
		"Brodowski",
		"Brotas",
		"Buritama",
		"Cachoeira Paulista",
		"Caconde",
		"Cafelândia",
		"Caieiras",
		"Cajamar",
		"Cajuru",
		"Campinas",
		"Campo Limpo Paulista",
		"Campos do Jordão",
		"Cananéia",
		"Capivari",
		"Caraguatatuba",
		"Carapicuiba",
		"Cardoso",
		"Casa Branca",
		"Catanduva",
		"Caçapava",
		"Cerqueira Cesar",
		"Cerquilho",
		"Chavantes",
		"Colina",
		"Conchal",
		"Conchas",
		"Cordeirópolis",
		"Cosmópolis",
		"Cotia",
		"Cravinhos",
		"Cruzeiro",
		"Cubatão",
		"Cunha",
		"Descalvado",
		"Diadema",
		"Dois Córregos",
		"Duartina",
		"Eldorado",
		"Embu das Artes",
		"Embu",
		"Espírito Santo do Pinhal",
		"Estrela D",
		"Fartura",
		"Fernandópolis",
		"Ferraz de Vasconcelos",
		"Franca",
		"Francisco Morato",
		"Franco da Rocha",
		"General Salgado",
		"Getulina",
		"Guararapes",
		"Guararema",
		"Guaratinguetá",
		"Guariba",
		"Guarujá",
		"Guarulhos",
		"Guará",
		"Guaíra",
		"Hortolândia",
		"Iacanga",
		"Ibaté",
		"Ibitinga",
		"Igarapava",
		"Iguape",
		"Ilha Solteira",
		"Ilhabela",
		"Ipaussu",
		"Ipuã",
		"Itajobi",
		"Itanhaém",
		"Itapecerica da Serra",
		"Itapevi",
		"Itapira",
		"Itaquaquecetuba",
		"Itariri",
		"Itatiba",
		"Itatinga",
		"Itaí",
		"Itirapina",
		"Itupeva",
		"Ituverava",
		"Itápolis",
		"Jaboticabal",
		"Jacareí",
		"Jacupiranga",
		"Jaguariúna",
		"Jales",
		"Jandira",
		"Jardinópolis",
		"Jarinu",
		"Jaú",
		"José Bonifácio",
		"Jundiaí",
		"Juquiá",
		"Laranjal Paulista",
		"Leme",
		"Lençóis Paulista",
		"Limeira",
		"Lins",
		"Lorena",
		"Louveira",
		"Macatuba",
		"Macaubal",
		"Mairiporã",
		"Matão",
		"Mauá",
		"Miguelópolis",
		"Miracatu",
		"Mirandópolis",
		"Mirassol",
		"Mococa",
		"Mogi Guaçu",
		"Mogi Mirim",
		"Mogi das Cruzes",
		"Mongaguá",
		"Monte Alto",
		"Monte Aprazível",
		"Monte Azul Paulista",
		"Monte Mor",
		"Morro Agudo",
		"Nazaré Paulista",
		"Neves Paulista",
		"Nhandeara",
		"Nova Granada",
		"Nova Odessa",
		"Novo Horizonte",
		"Nuporanga",
		"Olímpia",
		"Orlândia",
		"Osasco",
		"Ourinhos",
		"Ouroeste",
		"Palestina",
		"Palmeira D",
		"Paraibuna",
		"Paranapanema",
		"Pariquera",
		"Patrocínio Paulista",
		"Paulo de Faria",
		"Paulínia",
		"Pederneiras",
		"Pedregulho",
		"Pedreira",
		"Penápolis",
		"Pereira Barreto",
		"Peruíbe",
		"Pindamonhangaba",
		"Pinhalzinho",
		"Piquete",
		"Piracaia",
		"Piracicaba",
		"Piraju",
		"Pirajuí",
		"Pirangi",
		"Pirassununga",
		"Piratininga",
		"Pitangueiras",
		"Pontal",
		"Porto Ferreira",
		"Potirendaba",
		"Poá",
		"Praia Grande",
		"Promissão",
		"Queluz",
		"Registro",
		"Ribeirão Bonito",
		"Ribeirão Pires",
		"Ribeirão Preto",
		"Rio Claro",
		"Rio Grande de Serra",
		"Rio das Pedras",
		"Roseira",
		"Salesópolis",
		"Santa Adélia",
		"Santa Branca",
		"Santa Bárbara D",
		"Santa Cruz das Palmeiras",
		"Santa Cruz do Rio Pardo",
		"Santa Fé do Sul",
		"Santa Isabel",
		"Santa Rita do Passa Quatro",
		"Santa Rosa do Viterbo",
		"Santana do Parnaíba",
		"Santo André",
		"Santos",
		"Serra Negra",
		"Serrana",
		"Sertãozinho",
		"Socorro",
		"Sumaré",
		"Suzano",
		"São Bento do Sapucaí",
		"São Bernardo do Campo",
		"São Caetano do Sul",
		"São Carlos",
		"São Joaquim da Barra",
		"São José do Rio Pardo",
		"São José do Rio Preto",
		"São José dos Campos",
		"São João da Boa Vista",
		"São Luis do Paraitinga",
		"São Manuel",
		"São Paulo",
		"São Pedro",
		"São Sebastião",
		"São Sebastião da Grama",
		"São Simão",
		"São Vicente",
		"Tabapuã",
		"Taboão da Serra",
		"Tambaú",
		"Tanabi",
		"Taquaritinga",
		"Taquarituba",
		"Taubaté",
		"Tietê",
		"Tremembé",
		"Ubatuba",
		"Urupês",
		"Urânia",
		"Valinhos",
		"Valparaíso",
		"Vargem Grande Paulista",
		"Vargem Grande do Sul",
		"Vila Mimosa",
		"Vinhedo",
		"Viradouro",
		"Votuporanga",
		"Várzea Paulista"
	]	
	for (cidade of cidades) {
		const option = document.createElement("option")
		option.text = cidade
		city.appendChild(option)
	}
}

function sendData() {
	fetch
	// Recebe os dados do usuário
	var startDate = document.getElementById("start-date").value;
	var daysToAddDropdown = document.getElementById("days-to-add-dropdown");
	var daysToAddCustom = document.getElementById("days-to-add-custom");
	var daysToAdd = daysToAddDropdown.value;
	var city = document.getElementById("city").value;
	if (daysToAdd === "outro") {
		daysToAdd = daysToAddCustom.value;
	}

	if (city === "") {
		alert("Por favor, selecione uma cidade.");
		return;
	}

	// Envia os dados usando AJAX
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "/" + city, true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.onreadystatechange = function () {
		if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
			// Atualiza a pagina com o resultado
			var resultElement = document.getElementById("result");
			var backEndDate = new Date(xhr.responseText)
			backEndDate.setUTCHours(12)
			var resultText = "Prazo final: " + backEndDate
				.toLocaleDateString("pt-BR", { day: "numeric", month: "long", year: "numeric" });
			resultElement.innerHTML = resultText
			console.log(xhr.responseText)
		}
	};
	xhr.send("startDate=" + startDate + "&daysToAdd=" + daysToAdd);
}