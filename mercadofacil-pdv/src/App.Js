import React from "react";
import { Container, Typography } from "@mui/material";
import Produtos from "./Produtos";

function App() {
  return (
    <Container>
      <Typography variant="h4" sx={{mt:2}}>PDV — Produtos</Typography>
      <Produtos />
    </Container>
  );
}

export default App;
