-- Menu da nova operacao interfilial.
-- Banco: PostgreSQL.
-- Script idempotente: pode ser executado novamente sem duplicar opcoes.
--
-- Permissoes:
--   Nova venda interfilial herda os grupos do menu 5 (Consulta de pedidos).
--   Pedidos para atendimento herda os grupos do menu 26 (Consulta de transferencias).

DO $$
DECLARE
    v_menu_pai_id INTEGER;
    v_menu_venda_id INTEGER;
    v_menu_atendimento_id INTEGER;
BEGIN
    SELECT id_menuacesso
      INTO v_menu_pai_id
      FROM menuacesso
     WHERE id_parent IS NULL
       AND descricao = 'Operação Interfilial'
     ORDER BY id_menuacesso
     LIMIT 1;

    IF v_menu_pai_id IS NULL THEN
        INSERT INTO menuacesso
            (id_parent, descricao, alt, pgm, ordem, caminho)
        VALUES
            (
                NULL,
                'Operação Interfilial',
                'Venda e atendimento entre filiais',
                NULL,
                COALESCE(
                    (
                        SELECT MAX(ordem) + 1
                          FROM menuacesso
                         WHERE id_parent IS NULL
                    ),
                    1
                ),
                'Operação Interfilial'
            )
        RETURNING id_menuacesso INTO v_menu_pai_id;
    END IF;

    SELECT id_menuacesso
      INTO v_menu_venda_id
      FROM menuacesso
     WHERE id_parent = v_menu_pai_id
       AND pgm = 'pedvendainterfilial/novo.jsf'
     ORDER BY id_menuacesso
     LIMIT 1;

    IF v_menu_venda_id IS NULL THEN
        INSERT INTO menuacesso
            (id_parent, descricao, alt, pgm, ordem, caminho)
        VALUES
            (
                v_menu_pai_id,
                'Nova venda interfilial',
                'Criar venda utilizando estoque de outra filial',
                'pedvendainterfilial/novo.jsf',
                1,
                'Operação Interfilial'
            )
        RETURNING id_menuacesso INTO v_menu_venda_id;
    END IF;

    SELECT id_menuacesso
      INTO v_menu_atendimento_id
      FROM menuacesso
     WHERE id_parent = v_menu_pai_id
       AND pgm = 'pedatendimento/consulta.jsf'
     ORDER BY id_menuacesso
     LIMIT 1;

    IF v_menu_atendimento_id IS NULL THEN
        INSERT INTO menuacesso
            (id_parent, descricao, alt, pgm, ordem, caminho)
        VALUES
            (
                v_menu_pai_id,
                'Pedidos para atendimento',
                'Planejar separações, entregas e transferências',
                'pedatendimento/consulta.jsf',
                2,
                'Operação Interfilial'
            )
        RETURNING id_menuacesso INTO v_menu_atendimento_id;
    END IF;

    -- Mesmos grupos que já podem consultar pedidos.
    INSERT INTO menu_usuariogrupo
        (id_usuariogrupo, id_menuacesso)
    SELECT permissao.id_usuariogrupo, v_menu_venda_id
      FROM menu_usuariogrupo permissao
     WHERE permissao.id_menuacesso = 5
       AND NOT EXISTS (
            SELECT 1
              FROM menu_usuariogrupo existente
             WHERE existente.id_usuariogrupo =
                       permissao.id_usuariogrupo
               AND existente.id_menuacesso = v_menu_venda_id
       );

    -- Mesmos grupos que já podem consultar transferências.
    INSERT INTO menu_usuariogrupo
        (id_usuariogrupo, id_menuacesso)
    SELECT permissao.id_usuariogrupo, v_menu_atendimento_id
      FROM menu_usuariogrupo permissao
     WHERE permissao.id_menuacesso = 26
       AND NOT EXISTS (
            SELECT 1
              FROM menu_usuariogrupo existente
             WHERE existente.id_usuariogrupo =
                       permissao.id_usuariogrupo
               AND existente.id_menuacesso =
                       v_menu_atendimento_id
       );
END
$$;

-- Conferência das opções e permissões criadas.
SELECT
    menu.id_menuacesso,
    menu.id_parent,
    menu.descricao,
    menu.pgm,
    menu.ordem,
    COUNT(permissao.id_usuariogrupo) AS grupos_com_acesso
FROM menuacesso menu
LEFT JOIN menu_usuariogrupo permissao
       ON permissao.id_menuacesso = menu.id_menuacesso
WHERE menu.pgm IN (
    'pedvendainterfilial/novo.jsf',
    'pedatendimento/consulta.jsf'
)
GROUP BY
    menu.id_menuacesso,
    menu.id_parent,
    menu.descricao,
    menu.pgm,
    menu.ordem
ORDER BY menu.ordem;
