<?php

/**
 * Representa el la estructura de las Alumnoss
 * almacenadas en la base de datos
 */
require 'Database.php';

class Alumnos
{
    function __construct()
    {
    }

    /**
     * Obtiene los campos de un Alumno con un identificador
     * determinado
     *
     * @param $idAlumno Identificador del alumno
     * @return mixed
     */
    public static function getById($idDispositivo)
    {
        // Consulta de la tabla Alumnos
        $consulta = "SELECT fecha, temperatura, presion, humedad, radiacion
                             FROM mediciones
                             WHERE id_dispositivo = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($idDispositivo));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;

        } catch (PDOException $e) {
            // Aqu� puedes clasificar el error dependiendo de la excepci�n
            // para presentarlo en la respuesta Json
            return -1;
        }
    }

    /**
     * Actualiza un registro de la bases de datos basado
     * en los nuevos valores relacionados con un identificador
     *
     * @param $idAlumno      identificador
     * @param $nombre      nuevo nombre
     * @param $direccion nueva direccion

     */

}

?>
