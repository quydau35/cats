package com.endava.cats.fuzzer.fields;

import com.endava.cats.annotations.FieldFuzzer;
import com.endava.cats.fuzzer.executor.FieldsIteratorExecutor;
import com.endava.cats.fuzzer.fields.base.BaseReplaceFieldsFuzzer;
import com.endava.cats.json.JsonUtils;
import com.endava.cats.model.FuzzingData;
import io.github.ludovicianul.prettylogger.PrettyLogger;
import io.github.ludovicianul.prettylogger.PrettyLoggerFactory;
import jakarta.inject.Singleton;

import java.util.List;

@FieldFuzzer
@Singleton
public class ReplaceObjectsWithPrimitivesFieldsFuzzer extends BaseReplaceFieldsFuzzer {
    protected final PrettyLogger logger = PrettyLoggerFactory.getLogger(getClass());

    public ReplaceObjectsWithPrimitivesFieldsFuzzer(FieldsIteratorExecutor ce) {
        super(ce);
    }

    @Override
    public BaseReplaceFieldsFuzzer.BaseReplaceFieldsContext getContext(FuzzingData data) {
        return BaseReplaceFieldsFuzzer.BaseReplaceFieldsContext.builder()
                .replaceWhat("non-primitive")
                .replaceWith("primitive")
                .skipMessage("Fuzzer only runs for objects")
                .fieldFilter(field -> JsonUtils.isObject(data.getPayload(), field))
                .fuzzValueProducer(schema -> List.of("cats_primitive_string"))
                .build();
    }
}
