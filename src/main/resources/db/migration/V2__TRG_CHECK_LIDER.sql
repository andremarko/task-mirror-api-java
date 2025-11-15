CREATE OR REPLACE TRIGGER bfi_trg_check_lider
FOR INSERT OR UPDATE ON tbl_usuarios
                         COMPOUND TRIGGER

                         TYPE t_lider_ids IS TABLE OF NUMBER;
v_lider_ids t_lider_ids := t_lider_ids();

BEFORE EACH ROW IS
BEGIN
    IF :NEW.id_lider IS NOT NULL THEN
        v_lider_ids.EXTEND;
        v_lider_ids(v_lider_ids.COUNT) := :NEW.id_lider;
END IF;
END BEFORE EACH ROW;

AFTER STATEMENT IS
    v_role VARCHAR2(100);
BEGIN
FOR i IN 1 .. v_lider_ids.COUNT LOOP
SELECT role_usuario INTO v_role
FROM tbl_usuarios
WHERE id_usuario = v_lider_ids(i);

IF v_role != 'ROLE_SUPERIOR' THEN
            RAISE_APPLICATION_ERROR(
                -20001,
                'Não é permitido associar um líder que não tenha ROLE_SUPERIOR.'
            );
END IF;
END LOOP;
END AFTER STATEMENT;

END bfi_trg_check_lider;
/
