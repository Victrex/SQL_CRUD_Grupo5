- VERSION JDK USADA > 19.0
- VERSION SQL SERVER  MANAGEMENT STUDIO MANAGEMENT 19

# REQUISITOS
- Tener activada la opción "Modo de Autenticación de Windows y SQL Server" en el apartado de autenticación de servidor que está en las propiedades del servidor de Management Studio 19
- seguidamente de crear un usuario de nombre 'root' sin contraseña con todos los permisos autorizados.

- Cambiar a 'Enabled' el TCP/IP del 'Protocols for MSSQLSERVER' en el programa 'Sql Server Configuration Manager'
- Ademas de eso, debemos asegurarnos que:
  - Cuando abrimos las propiedades del TCP/IP todo este en 'yes' y en la pestaña de IP ADRESSESS en el ultimo apartado 'IPALL' el TCP PORT tenga de valor '1433'
- [ARCHIVO EJECUTABLE .jar](https://github.com/Victrex/SQL_CRUD_Grupo5/releases)
