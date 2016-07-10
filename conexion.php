<?php
//function conectar(){
  $conexion = null;
  $host = 'localhost';
  $db = 'sensores';
  $user = 'root';
  $pwd = 'qwertyuiop';
  try{
    $conexion = new PDO('mysql:host='.$host.';dbname='.$db, $user, $pwd);
    echo '<p>Conexion establecida</p>';
  }
  catch (PDOException $e){
    echo '<p>No se piede conectar a la bd</p>';
  }
  //return $conexion;
//}

 ?>
