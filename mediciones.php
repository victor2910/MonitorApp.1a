<?php
require('conexion.php');
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['id'])) {
        $con = Conectar();
        // Obtener parámetro idalumno
        $id = $_GET['id'];
        $SQL = 'SELECT * FROM mediciones WHERE id_dispositivo = :doc';
        $stmt = $con->prepare($SQL);
        $result = $stmt->execute(array(':doc'=>$id));
        $rows = $result->fetchAll(\PDO::FETCH_OBJ);
        if(count($rows)){
          echo "Si existen datos";
        }else{
          echo "No existen datos para tal id";
        }
    }
  }
 ?>
