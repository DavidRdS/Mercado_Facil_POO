import React, {useEffect, useState} from "react";
import axios from "axios";
import { List, ListItem, ListItemText, Button, TextField, Box } from "@mui/material";

export default function Produtos(){
  const [produtos, setProdutos] = useState([]);
  const [nome, setNome] = useState("");

  useEffect(()=> {
    carregar();
  }, []);

  function carregar(){
    axios.get("http://localhost:8080/api/produtos")
      .then(r => setProdutos(r.data))
      .catch(console.error);
  }

  function criar(){
    axios.post("http://localhost:8080/api/produtos", { nome, preco: 0.0 })
      .then(()=> { setNome(""); carregar(); })
      .catch(console.error);
  }

  return (
    <Box>
      <Box sx={{display:"flex", gap:1, my:2}}>
        <TextField label="Nome" value={nome} onChange={e=>setNome(e.target.value)} />
        <Button variant="contained" onClick={criar}>Criar</Button>
      </Box>
      <List>
        {produtos.map(p => (
          <ListItem key={p.id} divider>
            <ListItemText primary={p.nome} secondary={p.preco} />
          </ListItem>
        ))}
      </List>
    </Box>
  );
}
