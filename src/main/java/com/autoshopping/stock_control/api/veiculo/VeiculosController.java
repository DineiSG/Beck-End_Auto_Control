package com.autoshopping.stock_control.api.veiculo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/veiculos")
public class VeiculosController {

    @Autowired
    private VeiculosService service;

    /*Buscando todos os veiculos do banco de dados*/
    @GetMapping
    public ResponseEntity<Iterable<Veiculos>> get(){
        return ResponseEntity.ok(service.getVeiculos());
    }

    /*Buscando os veiculos de acordo com criterios*/

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Integer id){
        Optional<Veiculos> veiculos=service.getVeiculosById(id);
        return veiculos
                .map(Veiculos -> ResponseEntity.ok(veiculos))
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscando os dados da API do DENATRAN
    @GetMapping("/dados")
    public ResponseEntity<Map<String, Object>> getDados(@RequestParam String placa){
        HttpClient client=HttpClient.newHttpClient();

        String uri = "https://www.vistoriago.com.br/webservice/tp15/15BdvNacional.php?clie=7048&user=8001&cons=15&serial=c40d4850013e8351091314c7b29c4e&placa="+placa;

        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody=response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(responseBody, Map.class);

            return ResponseEntity.ok(jsonMap);

        }catch(Exception e){
            e.printStackTrace();
            return  ResponseEntity.status(500).body(Map.of("erro", "Erro ao obter dados para a placa: " +placa));
        }
    }

    @GetMapping("/fipe")
    public ResponseEntity<Map<String, Object>> getFipe(@RequestParam String placa) {
        HttpClient client = HttpClient.newHttpClient();

        String uri = "https://www.vistoriago.com.br/webservice/tp23/23DecodificadorFipe.php?cons=23&clie=7048&serial=c40d4850013e8351091314c7b29c4e&param=PLACA&cv=" + placa;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();

                // Remove o BOM (se presente)
                if (responseBody.startsWith("\uFEFF")) {
                    responseBody = responseBody.substring(1); // Remove o BOM
                }

                // Mapeando a resposta para um objeto JSON
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> jsonMap = objectMapper.readValue(responseBody, Map.class);

                // Obtendo os dados do veículo e da FIPE
                Map<String, Object> decodificador = (Map<String, Object>) ((Map<String, Object>) jsonMap.get("decodificador"));
                List<Map<String, Object>> codigosFipe = (List<Map<String, Object>>) ((Map<String, Object>) jsonMap.get("fipe")).get("codigos_fipe");

                // Obtendo o ano do modelo do decodificador
                String anoModelo = (String) decodificador.get("AnoModelo");

                // Encontrando o preço FIPE com base no ano do modelo
                for (Map<String, Object> codigoFipe : codigosFipe) {
                    List<Map<String, Object>> veiculos = (List<Map<String, Object>>) codigoFipe.get("veiculos");
                    for (Map<String, Object> veiculo : veiculos) {
                        String anoModeloFipe = (String) veiculo.get("ano_modelo");

                        // Comparando o ano modelo do veículo com o ano do modelo da FIPE
                        if (anoModelo.equals(anoModeloFipe)) {
                            String precoFipe = (String) veiculo.get("preco");

                            // Retornando o valor FIPE
                            Map<String, Object> result = Map.of(
                                    "veiculo", veiculo.get("veiculo"),
                                    "ano_modelo", anoModelo,
                                    "preco_fipe", precoFipe
                            );

                            return ResponseEntity.ok(result);
                        }
                    }
                }

                return ResponseEntity.status(404).body(Map.of("erro", "Veículo FIPE não encontrado para o ano modelo: " + anoModelo));
            } else {
                return ResponseEntity.status(response.statusCode()).body(Map.of("erro", "Erro ao obter dados, código de status: " + response.statusCode()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao obter dados para a placa: " + placa, "detalhes", e.getMessage()));
        }
    }


    @GetMapping("/unidade/{unidade}")
    public Iterable<Veiculos>getVeiculosByUnidade(@PathVariable("unidade")String unidade){
        return service.getVeiculosByUnidade(unidade);
    }

    @GetMapping("/modelo/{modelo}")
    public Iterable<Veiculos>getVeiculosByModelo(@PathVariable("modelo")String modelo){
        return service.getVeiculosByModelo(modelo);
    }

    /*Esse metodo foi criado para auxiliar na fuunção de atualizar um veiculo */
    @GetMapping("/placa/{placa}")
    public ResponseEntity getVeiculosByPlaca(@PathVariable("placa")String placa){
        Optional<Veiculos>veiculos=service.getVeiculosByPlaca(placa);
        return veiculos
                .map(Veiculos -> ResponseEntity.ok(veiculos))
                .orElse(ResponseEntity.notFound().build());
    }
 
    /*Cadastrando novos veiculos*/
    @PostMapping
    public ResponseEntity post (@RequestBody Veiculos veiculo) {
        Veiculos novo=service.insert(veiculo);
        return ResponseEntity.ok("Veiculo adicionado com sucesso");
    }

    /*Atualizando um veículo*/
    @PutMapping(path="/placa/{placa}")
    public ResponseEntity put(@PathVariable("placa") String placa, @RequestBody Veiculos veiculo){
        Veiculos atualizarVeiculo=service.update(veiculo, placa);
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Veiculo atualizado com sucesso");
        response.put("veiculo", atualizarVeiculo);
        return ResponseEntity.ok(response);
    }

    /*Deletando um veículo*/
    @Transactional
    @DeleteMapping(path="/placa/{placa}")
    public ResponseEntity delete (@PathVariable("placa")String placa){
        boolean ok=service.delete(placa);
        return ok?
                ResponseEntity.ok("Veiculo excluido com sucesso"):
                ResponseEntity.notFound().build();

    }


}