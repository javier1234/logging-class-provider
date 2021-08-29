package com.despegar.jav;


public interface Connector {

    /**
     * Realiza una busqueda basada en los parametros pasados en @param request
     *
     * Si existen en la base de datos mas registros que los solicitados en el parametro limit con el criterio de busqueda usado, retorna end en false.
     * Si el numero de resultados es menor a limit, entonces el valor de end es true.
     *
     * @param request
     * @return
     */
    Response search(Request request);

    /**
     * permite navegar por los resultados generados a partir de un search utilizando los parametros de request
     * @param request
     * @return
     */
    Response scroll(Request request);

    /**
     * Libera el scroll de la memoria
     */
    void clearScroll();

}
